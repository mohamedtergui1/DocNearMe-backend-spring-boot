package ma.tr.docnearme.modules.appointment;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.tr.docnearme.exception.PermissionException;
import ma.tr.docnearme.exception.ProcessNotCompletedException;
import ma.tr.docnearme.modules.clinic.Clinic;
import ma.tr.docnearme.modules.clinic.ClinicRepository;
import ma.tr.docnearme.modules.clinic.VacationPeriod;
import ma.tr.docnearme.modules.user.User;
import ma.tr.docnearme.modules.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;
    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;

    @Override
    public AppointmentResponse getAppointment(UUID id) {
        return appointmentMapper.toResponse(appointmentRepository.findById(id).orElseThrow(() -> new ProcessNotCompletedException(id.toString())));
    }

    @Override
    public AppointmentResponse updateAppointment(AppointmentRequest appointmentRequest, UUID id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new ProcessNotCompletedException("no such appointment"));

        if (appointment.getStatus() != AppointmentStatus.PENDING) {
            throw new ProcessNotCompletedException("you can't update a valid or rejected appointment");
        }
        if (appointmentRequest.startDateTime().isBefore(LocalDateTime.now())) {
            throw new ProcessNotCompletedException("you can't update an appointment to be in the past");
        }
        Clinic clinic = clinicRepository.findById(appointmentRequest.clinicId()).orElseThrow(() -> new ProcessNotCompletedException("clinic does not exist"));

        if (existsOverlappingAppointmentsWithClinicVacations(appointmentRequest.startDateTime(), appointmentRequest.endDateTime(), clinic.getVacations())) {
            throw new ProcessNotCompletedException("there is already an vacation in this range");
        }
        if (appointmentRepository.existsOverlappingAppointmentsExcludingCurrent(
                appointmentRequest.clinicId(),
                appointmentRequest.startDateTime(),
                appointmentRequest.endDateTime(),
                List.of(AppointmentStatus.PENDING, AppointmentStatus.VALID),
                id
        )) {
            throw new ProcessNotCompletedException("there is already an appointment in this range");
        }

        Appointment newAppointment = appointmentMapper.toAppointment(appointmentRequest);
        newAppointment.setId(id);
        newAppointment.setPatient(appointment.getPatient()); // Retain the original patient
        newAppointment.setIssueDate(appointment.getIssueDate());
        return appointmentMapper.toResponse(appointmentRepository.save(newAppointment));
    }

    @Override
    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest, UUID patientId) {
        if (appointmentRequest.startDateTime().isBefore(LocalDateTime.now())) {
            throw new ProcessNotCompletedException("you can't create an appointment in the past");
        }
        if (!userRepository.existsById(patientId)) {
            throw new ProcessNotCompletedException("patient does not exist");
        }
        Clinic clinic = clinicRepository.findById(appointmentRequest.clinicId()).orElseThrow(() -> new ProcessNotCompletedException("clinic does not exist"));

        if (existsOverlappingAppointmentsWithClinicVacations(appointmentRequest.startDateTime(), appointmentRequest.endDateTime(), clinic.getVacations())) {
            throw new ProcessNotCompletedException("there is already an vacation in this range");
        }

        if (appointmentRepository.existsOverlappingAppointments(
                appointmentRequest.clinicId(),
                appointmentRequest.startDateTime(),
                appointmentRequest.endDateTime(),
                List.of(AppointmentStatus.PENDING, AppointmentStatus.VALID)
        )) {
            throw new ProcessNotCompletedException("there is already an appointment in this range");
        }
        Appointment appointment = appointmentMapper.toAppointment(appointmentRequest);
        User patient = User.builder().id(patientId).build();
        appointment.setPatient(patient);
        appointment.setStatus(AppointmentStatus.PENDING);
        return appointmentMapper.toResponse(appointmentRepository.save(appointment));
    }

    private boolean existsOverlappingAppointmentsWithClinicVacations(
            @NotNull LocalDateTime start,
            @NotNull LocalDateTime end,
            @NotNull Set<VacationPeriod> vacations
    ) {
        return vacations.stream().anyMatch(vacationPeriod -> {
            LocalDateTime vacationStart = vacationPeriod.getStartDate().atStartOfDay();
            LocalDateTime vacationEnd = vacationPeriod.getEndDate().atStartOfDay();
            return start.isBefore(vacationEnd) && end.isAfter(vacationStart);
        });
    }

    @Override
    public void deleteAppointment(UUID id) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new ProcessNotCompletedException("no such appointment"));
        if (appointment.getStatus() == AppointmentStatus.VALID) {
            throw new ProcessNotCompletedException("you can't delete a valid appointment");
        }
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<AppointmentResponse> getAppointmentsByClinicIdAfterNowAndByDifferentStatus(UUID clinicId, AppointmentStatus appointmentStatus) {
        return appointmentRepository.findUpcomingAppointmentsByClinicId(clinicId, LocalDateTime.now(), appointmentStatus)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }







    @Override
    public List<AppointmentResponse> getAppointmentByClinicOwnerIdInDateRange(UUID clinicOwnerId, LocalDateTime start, LocalDateTime end) {
        return appointmentRepository.findAllByClinicClinicOwnerIdInDateRange(clinicOwnerId,start,end).stream().map(appointmentMapper::toResponse).toList();
    }

    @Override
    public List<AppointmentResponse> findAllByClinicClinicOwnerIdInDateRangeAndStatus(UUID medicineId, LocalDateTime start, LocalDateTime end, AppointmentStatus status) {
        return appointmentRepository.findAllByClinicClinicOwnerIdInDateRangeAndStatus(medicineId,start,end,status).stream().map(appointmentMapper::toResponse).toList();
    }

    @Override
    public List<AppointmentResponse> getAppointmentByPatientId(UUID id) {
        return appointmentRepository.findByPatientId(id).stream().map(appointmentMapper::toResponse).toList();
    }

}