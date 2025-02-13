package ma.tr.docnearme.modules.consultation;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {
    Consultation toEntity(ConsultationRequest consultationRequest);
    ConsultationResponse toResponse(ConsultationRequest consultationRequest);
}
