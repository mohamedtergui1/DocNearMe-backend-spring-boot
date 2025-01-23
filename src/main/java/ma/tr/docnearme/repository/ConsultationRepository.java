package ma.tr.docnearme.repository;

import ma.tr.docnearme.domain.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ConsultationRepository extends JpaRepository<Consultation, UUID> {
}
