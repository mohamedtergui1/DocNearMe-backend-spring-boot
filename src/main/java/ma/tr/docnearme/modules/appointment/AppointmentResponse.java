package ma.tr.docnearme.modules.appointment;


import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponse(UUID id,
                                  UUID patientId,
                                  UUID clinicId,
                                  AppointmentStatus status,
                                  LocalDateTime startDateTime,
                                  LocalDateTime endDateTime

) {

}