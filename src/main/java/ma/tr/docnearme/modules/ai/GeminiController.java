package ma.tr.docnearme.modules.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;



    @PostMapping("/generate")
    public ResponseEntity<String> generateContent(@RequestBody String prompt) {
        try {
            String response = geminiService.generateContent(prompt);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error processing your request: " + e.getMessage());
        }
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
