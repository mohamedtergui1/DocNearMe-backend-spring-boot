package ma.tr.docnearme.modules.medicalrecord;

import java.util.List;
import java.util.UUID;

public class MedicalRecordServiceImpl implements MedicalRecordService {
    @Override
    public MedicalRecordResponse createDossierMedical(MedicalRecordRequest medicalRecordRequest) {
        return null;
    }

    @Override
    public MedicalRecordResponse updateDossierMedical(MedicalRecordRequest medicalRecordRequest , UUID medicalRecordId) {
        return null;
    }

    @Override
    public void deleteDossierMedical(UUID dossierMedicalId) {

    }

    @Override
    public List<MedicalRecordResponse> getAllDossierMedical() {
        return List.of();
    }

    @Override
    public MedicalRecordResponse getDossierMedical(UUID dossierMedicalId) {
        return null;
    }
}
