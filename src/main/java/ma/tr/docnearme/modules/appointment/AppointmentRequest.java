package ma.tr.docnearme.modules.appointment;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentRequest(
        @NotNull
        UUID clinicId,
        String title,
        String description,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime
) {
}
