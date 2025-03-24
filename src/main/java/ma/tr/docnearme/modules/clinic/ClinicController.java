package ma.tr.docnearme.modules.clinic;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.modules.user.User;
import ma.tr.docnearme.util.dto.ApiResponse;
import ma.tr.docnearme.util.dto.PaginationMeta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/clinic")
@RestController
@RequiredArgsConstructor
public class ClinicController {
    private final ClinicService clinicService;

    @GetMapping
    public ApiResponse<List<ClinicResponse>> getClinics(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {


        Pageable pageable = PageRequest.of(page, size);

        Page<ClinicResponse> clinicPage = clinicService.findAllClinic(pageable);

        return ApiResponse.<List<ClinicResponse>>builder()
                .data(clinicPage.getContent())
                .meta(new PaginationMeta(clinicPage.getNumber(), clinicPage.getTotalPages(), clinicPage.getTotalElements()))  // Pagination info
                .build();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ClinicResponse> createClinic(@RequestBody @Valid ClinicRequest clinicRequest) {

        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ApiResponse.<ClinicResponse>builder()
                .data(clinicService.addClinic(clinicRequest, authUser.getId()))
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ClinicResponse> getClinic(@PathVariable UUID id) {
        return ApiResponse.<ClinicResponse>builder()
                .data(clinicService.getClinicById(id))
                .message("Clinic retrieved successfully")
                .build();
    }

    @PutMapping("/{id}")
    //PreAuthorize("authentication.principal.clinic != null && id== authentication.principal.clinic.id")
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

    @GetMapping("/forAuthUser")
    public ApiResponse<ClinicResponse> getClinicForAuthUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return ApiResponse.<ClinicResponse>builder()
                .data(clinicService.findByClinicOwnerId(user.getId()))
                .message(null)
                .build();
    }

}