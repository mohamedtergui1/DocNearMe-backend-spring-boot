package ma.tr.docnearme.modules.medicalrecord;

import ma.tr.docnearme.modules.medication.MedicationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {
    MedicalRecord toEntity(MedicalRecordRequest medicalRecordRequest);
    MedicalRecordResponse toResponse(MedicalRecord medicalRecord);
}
