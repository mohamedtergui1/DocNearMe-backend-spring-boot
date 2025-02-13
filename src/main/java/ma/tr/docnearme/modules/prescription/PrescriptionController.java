package ma.tr.docnearme.modules.prescription;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.modules.user.User;
import ma.tr.docnearme.util.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/prescription")
@RequiredArgsConstructor
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    @PostMapping
    public ResponseEntity<ApiResponse<PrescriptionResponse>> addPrescription(@RequestBody @Valid PrescriptionRequest prescriptionRequest) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PrescriptionResponse createdPrescription = prescriptionService.createPrescription(prescriptionRequest , authUser.getId());
        ApiResponse<PrescriptionResponse> response = ApiResponse.<PrescriptionResponse>builder()
                .message("Prescription created successfully")
                .data(createdPrescription)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePrescription(@PathVariable UUID id) {
        prescriptionService.deletePrescription(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Prescription deleted successfully")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }
}