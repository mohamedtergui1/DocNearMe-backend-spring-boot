package ma.tr.docnearme.modules.appointment;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {
    AppointmentResponse getAppointment(UUID id);

    AppointmentResponse updateAppointment(AppointmentRequest appointmentRequest, UUID prescriptionId , UUID patientId);

    AppointmentResponse createAppointment(AppointmentRequest appointmentRequest, UUID patientId);

    void deleteAppointment(UUID id);

    List<AppointmentResponse> getAppointmentsByClinicIdAfterNowAndByDeffrentStatus(UUID clinicId,AppointmentStatus status);
}
