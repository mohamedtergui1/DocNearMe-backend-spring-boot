package ma.tr.docnearme.mapper;

import ma.tr.docnearme.domain.entity.MedicalRecord;
import ma.tr.docnearme.dto.medicalrecord.MedicalRecordRequest;

public interface DossierMedicalMapper {
    MedicalRecord toEntity(MedicalRecordRequest medicalRecordRequest);
}
