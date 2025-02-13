package ma.tr.docnearme.modules.prescription;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PrescriptionRequest (
        @NotNull
        UUID clinicId
) {
}
