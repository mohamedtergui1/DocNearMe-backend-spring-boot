package ma.tr.docnearme.repository;

import ma.tr.docnearme.domain.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, UUID> {
}
