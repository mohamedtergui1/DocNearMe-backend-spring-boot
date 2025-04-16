package ma.tr.docnearme.modules.ai;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class GeminiService {

    @Value("${api.gemini.base-url}")
    private String baseUrl;

    @Value("${api.gemini.model}")
    private String model;

    @Value("${api.gemini.key}")
    private String apiKey;

    @Value("${api.gemini.timeout}")
    private int timeout;

    private final OkHttpClient httpClient;

    public GeminiService() {
        this.httpClient = new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.MILLISECONDS)
                .readTimeout(timeout, TimeUnit.MILLISECONDS)
                .writeTimeout(timeout, TimeUnit.MILLISECONDS)
                .build();
    }

    public String generateContent(String prompt) throws IOException {
        String url = String.format("%s/%s:generateContent?key=%s", baseUrl, model, apiKey);

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

        RequestBody body = RequestBody.create(
                requestBody.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response code: " + response.code());
            }

            JSONObject jsonResponse = new JSONObject(response.body().string());
            return extractGeneratedText(jsonResponse);
        }
    }

    public String chat(String messages) throws IOException {
        String url = String.format("%s/%s:generateContent?key=%s", baseUrl, model, apiKey);

        JSONObject requestBody = new JSONObject();
        JSONArray contents = new JSONArray();

        // Parse messages and structure them for the API
        // This assumes messages are in format "user:message1|assistant:response1|user:message2"
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

        RequestBody body = RequestBody.create(
                requestBody.toString(),
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

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
        if (candidates.length() == 0) {
            throw new RuntimeException("Empty candidates array");
        }

        JSONObject firstCandidate = candidates.getJSONObject(0);
        JSONObject content = firstCandidate.getJSONObject("content");
        JSONArray parts = content.getJSONArray("parts");

        if (parts.length() == 0) {
            throw new RuntimeException("No parts in response");
        }

        return parts.getJSONObject(0).getString("text");
    }
}