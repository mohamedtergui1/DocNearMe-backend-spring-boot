package ma.tr.docnearme.modules.ai;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiService {

    @Value("${api.gemini.base-url}")
    private String baseUrl;

    @Value("${api.gemini.model}")
    private String model;

    @Value("${api.gemini.key}")
    private String apiKey;

    private final OkHttpClient httpClient;


    public String generateContent(String prompt) throws IOException {
        String url = buildApiUrl();
        JSONObject requestBody = buildRequestPayload(prompt);

        Request request = createRequest(url, requestBody);
        return executeRequestAndExtractResponse(request);
    }

    public String chat(String messages) throws IOException {
        String url = buildApiUrl();
        log.info(url);
        JSONObject requestBody = buildChatPayload(messages);

        Request request = createRequest(url, requestBody);
        return executeRequestAndExtractResponse(request);
    }

    private String buildApiUrl() {
        return String.format("%s/%s:generateContent?key=%s", baseUrl, model, apiKey);
    }

    private JSONObject buildRequestPayload(String prompt) {
        JSONObject requestBody = new JSONObject();
        JSONArray contents = new JSONArray();
        JSONObject content = new JSONObject();
        JSONArray parts = new JSONArray();
        JSONObject part = new JSONObject();

        part.put("text", prompt);
        parts.put(part);
        content.put("parts", parts);
        contents.put(content);
        requestBody.put("contents", contents);

        return requestBody;
    }

    private JSONObject buildChatPayload(String messages) {
        JSONObject requestBody = new JSONObject();
        JSONArray contents = new JSONArray();
        String[] messagePairs = messages.split("\\|");

        for (String pair : messagePairs) {
            String[] roleAndContent = pair.split(":", 2);
            if (roleAndContent.length == 2) {
                JSONObject content = new JSONObject();
                JSONArray parts = new JSONArray();
                JSONObject part = new JSONObject();

                part.put("text", roleAndContent[1]);
                parts.put(part);
                content.put("parts", parts);
                content.put("role", roleAndContent[0]);
                contents.put(content);
            }
        }

        requestBody.put("contents", contents);
        return requestBody;
    }

    private Request createRequest(String url, JSONObject requestBody) {
        RequestBody body = RequestBody.create(
                requestBody.toString(),
                MediaType.parse("application/json")
        );

        return new Request.Builder()
                .url(url)
                .post(body)
                .build();
    }

    private String executeRequestAndExtractResponse(Request request) throws IOException {
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code());
            }

            JSONObject jsonResponse = new JSONObject(response.body().string());
            return extractGeneratedText(jsonResponse);
        }
    }

    private String extractGeneratedText(JSONObject jsonResponse) {
        if (!jsonResponse.has("candidates")) {
            throw new RuntimeException("No candidates in response");
        }

        JSONArray candidates = jsonResponse.getJSONArray("candidates");
        if (candidates.isEmpty()) {
            throw new RuntimeException("Empty candidates array");
        }

        JSONObject firstCandidate = candidates.getJSONObject(0);
        JSONObject content = firstCandidate.getJSONObject("content");
        JSONArray parts = content.getJSONArray("parts");

        if (parts.isEmpty()) {
            throw new RuntimeException("No parts in response");
        }

        return parts.getJSONObject(0).getString("text");
    }
}