package ma.tr.docnearme.modules.clinic;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.modules.user.User;
import ma.tr.docnearme.util.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/clinic")
@RestController
@RequiredArgsConstructor
public class ClinicController {
    private final ClinicService clinicService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('MEDICINE')")
    public ApiResponse<ClinicResponse> createClinic(@RequestBody @Valid ClinicRequest clinicRequest) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<ClinicResponse>builder()
                .data(clinicService.addClinic(clinicRequest, authUser.getId()))
                .build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MEDICINE', 'USER')")
    public ApiResponse<ClinicResponse> getClinic(@PathVariable UUID id) {
        return ApiResponse.<ClinicResponse>builder()
                .data(clinicService.getClinicById(id))
                .message("Clinic retrieved successfully")
                .build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MEDICINE')")
    public ApiResponse<ClinicResponse> updateClinic(
            @PathVariable UUID id,
            @RequestBody @Valid ClinicRequest clinicRequest
    ) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<ClinicResponse>builder()
                .data(clinicService.updateClinic(clinicRequest, id, authUser.getId()))
                .message("Clinic updated successfully")
                .build();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('MEDICINE')")
    public ApiResponse<Void> deleteClinic(@PathVariable UUID id) {
        clinicService.deleteClinic(id);
        return ApiResponse.<Void>builder()
                .message("Clinic deleted successfully")
                .build();
    }
}