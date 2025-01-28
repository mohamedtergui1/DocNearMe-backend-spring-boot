package ma.tr.docnearme.mapper;

import ma.tr.docnearme.domain.entity.MedicalRecord;
import ma.tr.docnearme.dto.medicalrecord.MedicalRecordRequest;
import ma.tr.docnearme.dto.medication.MedicationResponse;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring")
public interface DossierMedicalMapper {
    MedicalRecord toEntity(MedicalRecordRequest medicalRecordRequest);

    MedicationResponse toResponse(MedicalRecord medicalRecord);
}
