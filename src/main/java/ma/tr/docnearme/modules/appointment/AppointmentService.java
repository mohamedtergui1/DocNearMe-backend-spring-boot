package ma.tr.docnearme.modules.appointment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    AppointmentResponse getAppointment(UUID id);

    AppointmentResponse updateAppointment(AppointmentRequest appointmentRequest, UUID id);

    AppointmentResponse createAppointment(AppointmentRequest appointmentRequest, UUID patientId);

    void deleteAppointment(UUID id);

    List<AppointmentResponse> getAppointmentsByClinicIdAfterNowAndByDifferentStatus(UUID clinicId, AppointmentStatus status);

    boolean isAppointmentOwner(UUID appointmentId, UUID patientId);

    List<AppointmentResponse> getAppointmentByClinicOwnerIdInDateRange(UUID clinicOwnerId, LocalDateTime localDateTime, LocalDateTime localDateTime1);
}