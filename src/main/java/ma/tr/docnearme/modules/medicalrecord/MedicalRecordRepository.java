package ma.tr.docnearme.modules.medicalrecord;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, UUID> {
}
