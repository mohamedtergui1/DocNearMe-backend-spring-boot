package ma.tr.docnearme.modules.dosageschedule;

import java.time.LocalDate;
import java.util.UUID;

public record MedicationDosageScheduleRequest (
        int numberOfConsumptionInDay,
        String medicationId,
        Double quantity,
        Unit unit,
        boolean withFood,
        String specialInstructions,
        LocalDate dateWhenMustStopConsumption
) {
}
