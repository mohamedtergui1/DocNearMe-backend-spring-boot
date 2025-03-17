package ma.tr.docnearme.modules.appointment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.modules.user.User;
import ma.tr.docnearme.util.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/appointment")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<AppointmentResponse>> addAppointment(@RequestBody @Valid AppointmentRequest appointmentRequest) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppointmentResponse createdAppointment = appointmentService.createAppointment(appointmentRequest, authUser.getId());
        ApiResponse<AppointmentResponse> response = ApiResponse.<AppointmentResponse>builder()
                .message("Appointment created successfully")
                .data(createdAppointment)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@appointmentServiceImpl.isAppointmentOwner(#id, authentication.principal.id)")
    public ResponseEntity<ApiResponse<Void>> deleteAppointment(@PathVariable UUID id) {
        appointmentService.deleteAppointment(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Appointment deleted successfully")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@appointmentServiceImpl.isAppointmentOwner(#id, authentication.principal.id)")
    public ResponseEntity<ApiResponse<AppointmentResponse>> updateAppointment(@PathVariable UUID id, @RequestBody @Valid AppointmentRequest appointmentRequest) {
        AppointmentResponse updatedAppointment = appointmentService.updateAppointment(appointmentRequest, id);
        ApiResponse<AppointmentResponse> response = ApiResponse.<AppointmentResponse>builder()
                .message("Appointment updated successfully")
                .data(updatedAppointment)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAppointmentsByClinicId/{clinicId}")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getActiveAppointmentsByClinicId(@PathVariable UUID clinicId) {
        List<AppointmentResponse> appointments = appointmentService.getAppointmentsByClinicIdAfterNowAndByDifferentStatus(clinicId, AppointmentStatus.CANCELLED);
        ApiResponse<List<AppointmentResponse>> response = ApiResponse.<List<AppointmentResponse>>builder()
                .message("Appointments retrieved successfully")
                .data(appointments)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAppointmentForAuthUserClinic")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentForAuthUserClinic(@RequestParam(required = false) LocalDate start, @RequestParam(required = false) LocalDate end) {
        if (start == null) {
            start = LocalDate.now().minusWeeks(1);
        }
        if (end == null) {
            end = LocalDate.now().plusWeeks(1);
        }
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<AppointmentResponse> appointments = appointmentService.getAppointmentByClinicOwnerIdInDateRange(authUser.getId(), start.atStartOfDay(), end.atStartOfDay());
        ApiResponse<List<AppointmentResponse>> response = ApiResponse.<List<AppointmentResponse>>builder()
                .data(appointments)
                .build();
        return ResponseEntity.ok(response);
    }
}