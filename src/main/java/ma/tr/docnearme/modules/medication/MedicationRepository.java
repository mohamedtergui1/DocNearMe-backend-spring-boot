package ma.tr.docnearme.modules.medication;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicationRepository extends JpaRepository<Medication, String> {
}
