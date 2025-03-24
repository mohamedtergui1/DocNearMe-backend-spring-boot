package ma.tr.docnearme.modules.dosageschedule;

import java.util.List;
import java.util.UUID;

public interface MedicationDosageScheduleService {
    List<MedicationDosageScheduleResponse> findByConsultationMedicalRecordPatientId(UUID id);
}
