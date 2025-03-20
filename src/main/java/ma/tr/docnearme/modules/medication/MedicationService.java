package ma.tr.docnearme.modules.medication;

import java.util.List;

public interface MedicationService {
    MedicationResponse addMedication(MedicationRequest medicationRequest);

    MedicationResponse updateMedication(MedicationRequest medicationRequest, String medicationId);

    void deleteMedication(MedicationRequest medicationRequest);

    MedicationResponse getMedication(String medicationId);

    List<MedicationResponse> getAllMedications();
    List<MedicationResponse> searchMedications(String medicationName);
}
