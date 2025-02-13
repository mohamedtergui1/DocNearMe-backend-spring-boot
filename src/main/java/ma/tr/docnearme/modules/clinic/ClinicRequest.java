package ma.tr.docnearme.modules.clinic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ClinicRequest(
        @NotBlank
        String clinicName,
        @NotBlank
        String clinicAddress,
        @NotNull
        UUID categoryId) {
}
