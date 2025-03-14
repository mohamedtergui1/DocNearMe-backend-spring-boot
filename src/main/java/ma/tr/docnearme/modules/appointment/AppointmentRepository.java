package ma.tr.docnearme.modules.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    @Query("SELECT a FROM Appointment a WHERE a.clinic.id = :clinicId AND a.endDateTime > :now AND a.status <> :status")
    List<Appointment> findByClinicClinicOwnerOwnerIdAndIssueDateAfterNowAndStatusNotAsParam(
            @Param("clinicId") UUID clinicId,
            @Param("now") LocalDateTime now,
            @Param("status") AppointmentStatus status
    );

}