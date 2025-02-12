package ma.tr.docnearme.modules.medication;

import java.util.List;
import java.util.UUID;

public class MedicationServiceImpl implements MedicationService {
    @Override
    public MedicationResponse addMedication(MedicationRequest medicationRequest) {
        return null;
    }

    @Override
    public MedicationResponse updateMedication(MedicationRequest medicationRequest, UUID medicationId) {
        return null;
    }

    @Override
    public void deleteMedication(MedicationRequest medicationRequest) {

    }

    @Override
    public MedicationResponse getMedication(UUID medicationId) {
        return null;
    }

    @Override
    public List<MedicationResponse> getAllMedications() {
        return List.of();
    }
}
