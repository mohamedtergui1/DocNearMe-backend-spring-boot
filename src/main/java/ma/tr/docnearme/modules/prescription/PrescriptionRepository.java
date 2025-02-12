package ma.tr.docnearme.modules.prescription;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PrescriptionRepository extends JpaRepository<Prescription, UUID> {
}
