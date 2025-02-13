package ma.tr.docnearme.modules.consultation;

import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.util.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consultation")
@RequiredArgsConstructor
public class ConsultationController {
    private final ConsultationService consultationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ConsultationResponse> addConsultation(ConsultationRequest consultationRequest) {
        ConsultationResponse createdConsultation = consultationService.createConsultation(consultationRequest);
        return ApiResponse.<ConsultationResponse>builder().data(createdConsultation)
                .message("consultation created successfully").build();
    }
}
