package ma.tr.docnearme.service.prescription;

import ma.tr.docnearme.dto.prescription.PrescriptionRequest;
import ma.tr.docnearme.dto.prescription.PrescriptionResponse;

import java.util.UUID;

public interface PrescriptionService {
    PrescriptionResponse getPrescription(UUID id);

    PrescriptionResponse updatePrescription(PrescriptionRequest prescriptionRequest);

    PrescriptionResponse createPrescription(PrescriptionRequest prescriptionRequest);

    void deletePrescription(PrescriptionRequest prescriptionRequest);
}
