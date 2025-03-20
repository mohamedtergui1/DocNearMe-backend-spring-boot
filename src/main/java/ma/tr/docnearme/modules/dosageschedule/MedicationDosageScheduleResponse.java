package ma.tr.docnearme.modules.dosageschedule;

import java.util.UUID;

public record MedicationDosageScheduleResponse(
        UUID id,
        int numberOfConsumptionInDay,
        String medicationId,
        UUID consultationId,
        Double quantity,
        Unit unit,
        boolean withFood,
        String specialInstructions
) {
}
