package ma.tr.docnearme.repository;

import ma.tr.docnearme.domain.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {
}
