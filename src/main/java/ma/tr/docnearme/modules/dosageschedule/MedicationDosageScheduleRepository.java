package ma.tr.docnearme.modules.dosageschedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicationDosageScheduleRepository extends JpaRepository<MedicationDosageSchedule, UUID> {

}
