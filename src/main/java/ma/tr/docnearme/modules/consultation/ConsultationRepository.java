package ma.tr.docnearme.modules.consultation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ConsultationRepository extends JpaRepository<Consultation, UUID> {
    Optional<Consultation> findByAppointmentId(UUID appointmentId);

    Page<Consultation> findByClinicClinicOwnerId(UUID ownerId, Pageable pageable);
    @Query("SELECT c FROM Consultation c WHERE c.medicalRecord.patient.id = :patientId")
    Page<Consultation> findByMedicalRecordPatientId(UUID patientId, Pageable pageable);

    Page<Consultation> findByAppointmentId(UUID appointmentId, Pageable pageable);
}
