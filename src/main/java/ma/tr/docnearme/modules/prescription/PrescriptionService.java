package ma.tr.docnearme.modules.prescription;

import java.util.UUID;

public interface PrescriptionService {
    PrescriptionResponse getPrescription(UUID id);

    PrescriptionResponse updatePrescription(PrescriptionRequest prescriptionRequest, UUID prescriptionId);

    PrescriptionResponse createPrescription(PrescriptionRequest prescriptionRequest);

    void deletePrescription(UUID id);
}
