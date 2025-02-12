package ma.tr.docnearme.modules.medicalrecord;

import ma.tr.docnearme.modules.medication.MedicationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicalRecordMapper {
    MedicalRecord toEntity(MedicalRecordRequest medicalRecordRequest);

    MedicationResponse toResponse(MedicalRecord medicalRecord);
}
