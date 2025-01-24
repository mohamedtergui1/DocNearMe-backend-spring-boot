package ma.tr.docnearme.mapper;

import ma.tr.docnearme.domain.entity.Consultation;
import ma.tr.docnearme.dto.consultation.ConsultationRequest;
import ma.tr.docnearme.dto.consultation.ConsultationResponse;

public interface ConsultationMapper {
    Consultation toEntity(ConsultationRequest consultationRequest);

    ConsultationResponse toResponse(ConsultationRequest consultationRequest);
}
