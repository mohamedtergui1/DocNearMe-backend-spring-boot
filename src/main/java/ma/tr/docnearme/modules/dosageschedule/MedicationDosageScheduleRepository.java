package ma.tr.docnearme.modules.dosageschedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface MedicationDosageScheduleRepository extends JpaRepository<MedicationDosageSchedule, UUID> {
    @Query("SELECT md FROM MedicationDosageSchedule md WHERE md.consultation.medicalRecord.patient.id = :consultationMedicalRecordPatientId")
    List<MedicationDosageSchedule> findByConsultationMedicalRecordPatientId(UUID consultationMedicalRecordPatientId);
}
