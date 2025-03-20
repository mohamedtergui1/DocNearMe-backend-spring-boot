package ma.tr.docnearme.modules.dosageschedule;

import ma.tr.docnearme.modules.medication.MedicationResponse;

import java.util.UUID;

public record MedicationDosageScheduleResponse(
        UUID id,
        int numberOfConsumptionInDay,
        MedicationResponse medication,
        UUID consultationId,
        Double quantity,
        Unit unit,
        boolean withFood,
        String specialInstructions
) {
}
