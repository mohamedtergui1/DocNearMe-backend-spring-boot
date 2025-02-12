package ma.tr.docnearme.modules.medicalrecord;

import java.util.List;
import java.util.UUID;

public interface MedicalRecordService {
    MedicalRecordResponse createDossierMedical(MedicalRecordRequest medicalRecordRequest);

    MedicalRecordResponse updateDossierMedical(MedicalRecordRequest medicalRecordRequest , UUID medicalRecordId);

    void deleteDossierMedical(UUID dossierMedicalId);

    List<MedicalRecordResponse> getAllDossierMedical();

    MedicalRecordResponse getDossierMedical(UUID dossierMedicalId);
}
