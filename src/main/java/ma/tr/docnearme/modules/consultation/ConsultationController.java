package ma.tr.docnearme.modules.consultation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.modules.user.User;
import ma.tr.docnearme.util.dto.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/consultation")
@RequiredArgsConstructor
public class ConsultationController {
    private final ConsultationService consultationService;

    @PostMapping("/{appointmentId}")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@consultationServiceImpl.checkIfCanCreateConsultation(#consultationRequest.appointmentId(), authentication.principal.id)")
    public ApiResponse<ConsultationResponse> addConsultation(
            @PathVariable UUID appointmentId,
            @RequestBody @Valid ConsultationRequest consultationRequest) {
        ConsultationResponse createdConsultation = consultationService.createConsultation(consultationRequest);
        return ApiResponse.<ConsultationResponse>builder()
                .data(createdConsultation)
                .message("Consultation created successfully")
                .build();
    }

    @GetMapping("/getConsultationByAppointmentId/{appointmentId}")
    @PreAuthorize("@consultationServiceImpl.checkIfCanCanOpenTheConsultation(#appointmentId, authentication.principal.id)")
    public ApiResponse<ConsultationResponse> getConsultationByAppointmentId(
            @PathVariable UUID appointmentId) {
        ConsultationResponse createdConsultation = consultationService.getConsultationByAppointmentId(appointmentId);
        return ApiResponse.<ConsultationResponse>builder()
                .data(createdConsultation)
                .build();
    }

    @GetMapping("/getConsultationsForAuthMedicine")
    public ApiResponse<List<ConsultationResponse>> getConsultationsForAuthMedicine(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10")  int size
    ) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(page, size);

        Page<ConsultationResponse> consultations = consultationService.getConsultationsByMedicineId(pageable,authUser.getId());

        return ApiResponse.<List<ConsultationResponse>>builder().data(consultations.getContent()).build();
    }
}