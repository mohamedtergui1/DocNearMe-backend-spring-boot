package ma.tr.docnearme.modules.consultation;

import ma.tr.docnearme.modules.clinic.ClinicResponse;
import ma.tr.docnearme.modules.dosageschedule.MedicationDosageScheduleResponse;
import ma.tr.docnearme.modules.medicalrecord.MedicalRecord;
import ma.tr.docnearme.modules.medicalrecord.MedicalRecordResponse;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ConsultationResponse(
        UUID id,
        UUID appointmentId,
        ClinicResponse clinic,
        MedicalRecordResponse medicalRecord,
        LocalDateTime consultationDate,
        String reason,
        int recoveryDays,
        List<MedicationDosageScheduleResponse> medicationsDosageSchedule
) {
}