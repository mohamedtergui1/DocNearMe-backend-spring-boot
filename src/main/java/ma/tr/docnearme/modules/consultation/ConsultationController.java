package ma.tr.docnearme.modules.consultation;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.util.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/consultation")
@RequiredArgsConstructor
public class ConsultationController {
    private final ConsultationService consultationService;

    @PostMapping("/{appointmentId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@consultationServiceImpl.checkIfCanCreateConsultation(#appointmentId, authentication.principal.id)")
    public ApiResponse<ConsultationResponse> addConsultation(
            @PathVariable UUID appointmentId,
            @RequestBody @Valid ConsultationRequest consultationRequest) {
        ConsultationResponse createdConsultation = consultationService.createConsultation(consultationRequest, appointmentId);
        return ApiResponse.<ConsultationResponse>builder()
                .data(createdConsultation)
                .message("Consultation created successfully")
                .build();
    }

    @GetMapping("/getConsultationByAppointmentId/{appointmentId}")
    @PreAuthorize("@consultationServiceImpl.checkIfCanCreateConsultation(#appointmentId, authentication.principal.id)")
    public ApiResponse<ConsultationResponse> getConsultationByAppointmentId(
            @PathVariable UUID appointmentId) {
        ConsultationResponse createdConsultation = consultationService.getConsultationByAppointmentId(appointmentId);
        return ApiResponse.<ConsultationResponse>builder()
                .data(createdConsultation)
                .message("Consultation created successfully")
                .build();
    }
}