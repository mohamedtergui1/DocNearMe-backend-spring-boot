package ma.tr.docnearme.modules.consultation;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ma.tr.docnearme.modules.dosageschedule.MedicationDosageScheduleRequest;

import java.util.List;
import java.util.UUID;

public record ConsultationRequest (
        @NotNull
        UUID appointmentId,
        @NotNull
        UUID clinicId,
        @NotBlank
        String reason,
        @Positive
        int recoveryDays,
        List<MedicationDosageScheduleRequest> medicationsDosageSchedule
) {
}
