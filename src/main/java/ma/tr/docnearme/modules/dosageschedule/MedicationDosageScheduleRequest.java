package ma.tr.docnearme.modules.dosageschedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
import java.util.UUID;

public record MedicationDosageScheduleRequest (
        @Positive
        int numberOfConsumptionInDay,
        @NotBlank
        String medicationId,
        @Positive
        Double quantity,
        Unit unit,
        boolean withFood,
        String specialInstructions,
        LocalDate dateWhenMustStopConsumption
) {
}
