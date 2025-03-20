package ma.tr.docnearme.modules.consultation;

import ma.tr.docnearme.modules.dosageschedule.MedicationDosageScheduleResponse;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ConsultationResponse(
        UUID id,
        UUID clinicId,
        UUID medicalRecordId,
        LocalDateTime consultationDate,
        String reason,
        int recoveryDays,
        List<MedicationDosageScheduleResponse> medicationsDosageSchedule
) {}