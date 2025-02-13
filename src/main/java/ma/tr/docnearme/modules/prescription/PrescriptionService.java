package ma.tr.docnearme.modules.prescription;

import java.util.UUID;

public interface PrescriptionService {
    PrescriptionResponse getPrescription(UUID id);

    PrescriptionResponse updatePrescription(PrescriptionRequest prescriptionRequest, UUID prescriptionId , UUID patientId);

    PrescriptionResponse createPrescription(PrescriptionRequest prescriptionRequest , UUID patientId);

    void deletePrescription(UUID id);
}
