package ma.tr.docnearme.repository;

import ma.tr.docnearme.domain.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicationRepository extends JpaRepository<Medication, UUID> {
}
