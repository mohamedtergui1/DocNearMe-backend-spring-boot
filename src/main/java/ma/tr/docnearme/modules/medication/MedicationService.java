package ma.tr.docnearme.modules.medication;

import java.util.List;
import java.util.UUID;

public interface MedicationService {
    MedicationResponse addMedication(MedicationRequest medicationRequest);

    MedicationResponse updateMedication(MedicationRequest medicationRequest, UUID medicationId);

    void deleteMedication(MedicationRequest medicationRequest);

    MedicationResponse getMedication(UUID medicationId);

    List<MedicationResponse> getAllMedications();
}
