package ma.tr.docnearme.service.consultation;

import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.dto.consultation.ConsultationRequest;
import ma.tr.docnearme.dto.consultation.ConsultationResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsultationServiceImpl implements  ConsultationService {
    @Override
    public ConsultationResponse getConsultation(UUID consultationId) {
        return null;
    }

    @Override
    public ConsultationResponse createConsultation(ConsultationRequest consultationRequest) {
        return null;
    }

    @Override
    public ConsultationResponse updateConsultation(ConsultationRequest consultationRequest , UUID consultationId) {
        return null;
    }

    @Override
    public void deleteConsultation(UUID consultationId) {

    }

    @Override
    public List<ConsultationResponse> getConsultations() {
        return List.of();
    }
}
