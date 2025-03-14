package ma.tr.docnearme.modules.appointment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentRequest(
        @NotNull
        UUID clinicId,
        @NotBlank
        String subject,
        @NotNull
        LocalDateTime startDateTime,
        @NotNull
        LocalDateTime endDateTime,
        AppointmentStatus status
) {
}
