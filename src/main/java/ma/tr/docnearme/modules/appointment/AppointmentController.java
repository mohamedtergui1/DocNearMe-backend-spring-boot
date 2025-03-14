package ma.tr.docnearme.modules.appointment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.modules.user.User;
import ma.tr.docnearme.util.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteAppointment(@PathVariable UUID id) {
        appointmentService.deleteAppointment(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Appointment deleted successfully")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updateAppointment(@PathVariable UUID id, @RequestBody @Valid AppointmentRequest appointmentRequest) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        appointmentService.updateAppointment(appointmentRequest,id,authUser.getId());
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .message("Appointment updated successfully")
                .data(null)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAppointmentsByClinicId/{clinicId}")
    public ResponseEntity<ApiResponse<List<AppointmentResponse>>> getAppointmentsByClinicIdAfterNowAndNotRejected(@PathVariable UUID clinicId) {
        List<AppointmentResponse> listAppointments = appointmentService.getAppointmentsByClinicIdAfterNowAndByDeffrentStatus(clinicId, AppointmentStatus.CANCELLED);
        ApiResponse<List<AppointmentResponse>> response = ApiResponse.<List<AppointmentResponse>>builder()
                .data(listAppointments)
                .build();
        return ResponseEntity.ok(response);
    }
}