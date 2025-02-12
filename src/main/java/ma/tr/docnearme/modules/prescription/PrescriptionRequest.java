package ma.tr.docnearme.modules.prescription;

import java.util.UUID;

public record PrescriptionRequest (
        UUID patientId
        ,
        UUID clinicId
) {
}
