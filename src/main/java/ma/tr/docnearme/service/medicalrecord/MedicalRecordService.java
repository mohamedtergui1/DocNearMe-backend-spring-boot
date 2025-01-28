package ma.tr.docnearme.service.medicalrecord;

import ma.tr.docnearme.dto.medicalrecord.MedicalRecordRequest;
import ma.tr.docnearme.dto.medicalrecord.MedicalRecordResponse;

import java.util.List;
import java.util.UUID;

public interface MedicalRecordService {
    MedicalRecordResponse createDossierMedical(MedicalRecordRequest medicalRecordRequest);

    MedicalRecordResponse updateDossierMedical(MedicalRecordRequest medicalRecordRequest , UUID medicalRecordId);

    void deleteDossierMedical(UUID dossierMedicalId);

    List<MedicalRecordResponse> getAllDossierMedical();

    MedicalRecordResponse getDossierMedical(UUID dossierMedicalId);
}
