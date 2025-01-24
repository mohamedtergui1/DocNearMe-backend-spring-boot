package ma.tr.docnearme.service.medication;

import ma.tr.docnearme.dto.medication.MedicationRequest;
import ma.tr.docnearme.dto.medication.MedicationResponse;

import java.util.List;
import java.util.UUID;

public interface MedicationService {
    MedicationResponse addMedication(MedicationRequest medicationRequest);

    MedicationResponse updateMedication(MedicationRequest medicationRequest);

    void deleteMedication(MedicationRequest medicationRequest);

    MedicationResponse getMedication(UUID medicationId);

    List<MedicationResponse> getAllMedications();
}
