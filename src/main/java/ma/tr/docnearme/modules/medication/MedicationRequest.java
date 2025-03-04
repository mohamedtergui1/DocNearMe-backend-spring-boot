package ma.tr.docnearme.modules.medication;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public record MedicationRequest(
        @NotBlank
        String name,
        @NotBlank
        String instructions,
        @NotBlank
        String ingredients,
        @NotBlank
        String Forbidden
) {
}
