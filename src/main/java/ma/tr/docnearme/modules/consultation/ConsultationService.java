package ma.tr.docnearme.modules.consultation;

import java.util.List;
import java.util.UUID;

public interface ConsultationService {
    ConsultationResponse getConsultation(UUID consultationId);

    ConsultationResponse createConsultation(ConsultationRequest consultationRequest, UUID appointmentId);

    public boolean checkIfCanCreateConsultation(UUID AppointmentId, UUID authUserID);

    ConsultationResponse getConsultationByAppointmentId(UUID appointmentId);
}
