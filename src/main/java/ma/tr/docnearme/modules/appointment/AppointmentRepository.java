package ma.tr.docnearme.modules.appointment;

import com.github.javafaker.App;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    @Query("SELECT a FROM Appointment a " +
            "WHERE a.clinic.id = :clinicId " +
            "AND a.endDateTime > :now " +
            "AND a.status <> :status")
    List<Appointment> findUpcomingAppointmentsByClinicId(
            @Param("clinicId") UUID clinicId,
            @Param("now") LocalDateTime now,
            @Param("status") AppointmentStatus status
    );

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM Appointment a " +
            "WHERE a.clinic.id = :clinicId " +
            "AND a.startDateTime < :endDate " +
            "AND a.endDateTime > :startDate " +
            "AND a.status IN :statuses")
    Boolean existsOverlappingAppointments(
            @Param("clinicId") UUID clinicId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("statuses") List<AppointmentStatus> statuses
    );

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN TRUE ELSE FALSE END FROM Appointment a " +
            "WHERE a.clinic.id = :clinicId " +
            "AND a.startDateTime < :endDate " +
            "AND a.endDateTime > :startDate " +
            "AND a.status IN :statuses " +
            "AND a.id <> :appointmentId")
    Boolean existsOverlappingAppointmentsExcludingCurrent(
            @Param("clinicId") UUID clinicId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("statuses") List<AppointmentStatus> statuses,
            @Param("appointmentId") UUID appointmentId
    );

    @Query("SELECT a FROM Appointment a " +
            "WHERE a.clinic.clinicOwner.id = :clinicOwnerId " +
            "AND a.startDateTime > :startDate " +
            "AND a.endDateTime < :endDate")
    List<Appointment> findAllByClinicClinicOwnerIdInDateRange(
            @Param("clinicOwnerId") UUID clinicOwnerId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    @Query("SELECT a FROM Appointment a " +
            "WHERE a.clinic.clinicOwner.id = :clinicOwnerId " +
            "AND a.startDateTime >= :startDate " +
            "AND a.endDateTime <= :endDate " +
            "AND a.status = :status")
    List<Appointment> findAllByClinicClinicOwnerIdInDateRangeAndStatus(
            @Param("clinicOwnerId") UUID clinicOwnerId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            @Param("status") AppointmentStatus status
    );

}