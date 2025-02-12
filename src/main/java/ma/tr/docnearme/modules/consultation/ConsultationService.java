package ma.tr.docnearme.modules.consultation;

import java.util.List;
import java.util.UUID;

public interface ConsultationService {
    ConsultationResponse getConsultation(UUID consultationId);

    ConsultationResponse createConsultation(ConsultationRequest consultationRequest);

    ConsultationResponse updateConsultation(ConsultationRequest consultationRequest , UUID consultationId);

    void deleteConsultation(UUID consultationId);

    List<ConsultationResponse> getConsultations();
}
