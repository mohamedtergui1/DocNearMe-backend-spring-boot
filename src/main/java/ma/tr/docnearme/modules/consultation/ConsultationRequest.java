package ma.tr.docnearme.modules.consultation;

import ma.tr.docnearme.modules.dosageschedule.MedicationDosageScheduleRequest;

import java.util.List;
import java.util.UUID;

public record ConsultationRequest (

        UUID clinicId,
        String reason,
        int recoveryDays,
        List<MedicationDosageScheduleRequest> medicationsDosageSchedule
) {
}
