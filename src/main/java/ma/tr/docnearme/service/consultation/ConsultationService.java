package ma.tr.docnearme.service.consultation;

import ma.tr.docnearme.dto.consultation.ConsultationRequest;
import ma.tr.docnearme.dto.consultation.ConsultationResponse;

import java.util.List;
import java.util.UUID;

public interface ConsultationService {
    ConsultationResponse getConsultation(UUID consultationId);

    ConsultationResponse createConsultation(ConsultationRequest consultationRequest);

    ConsultationResponse updateConsultation(ConsultationRequest consultationRequest);

    void deleteConsultation(UUID consultationId);

    List<ConsultationResponse> getConsultations();
}
