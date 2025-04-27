package ma.tr.docnearme.modules.ai;

import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.util.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;



    @PostMapping("/generate")
    public ApiResponse<String> generateContent(@RequestBody String prompt) throws IOException {

            String response = geminiService.generateContent(prompt);
            return  ApiResponse.<String>builder().data(response).build();

    }

    @PostMapping("/chat")
    public ResponseEntity<String> chat(@RequestBody ChatRequest chatRequest) {
        try {
            String response = geminiService.chat(chatRequest.message());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error processing your chat request: " + e.getMessage());
        }
    }
}
