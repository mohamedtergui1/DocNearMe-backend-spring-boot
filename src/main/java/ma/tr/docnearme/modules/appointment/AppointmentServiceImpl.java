package ma.tr.docnearme.modules.appointment;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import ma.tr.docnearme.exception.PermissionException;
import ma.tr.docnearme.exception.ProcessNotCompletedException;
import ma.tr.docnearme.modules.clinic.ClinicRepository;
import ma.tr.docnearme.modules.user.User;
import ma.tr.docnearme.modules.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
        if (!clinicRepository.existsById(appointmentRequest.clinicId())) {
            throw new ProcessNotCompletedException("clinic does not exist");
        }

        Appointment newAppointment = appointmentMapper.toAppointment(appointmentRequest);
        newAppointment.setId(id);
        newAppointment.setPatient(appointment.getPatient()); // Retain the original patient
        newAppointment.setIssueDate(appointment.getIssueDate());
        return appointmentMapper.toResponse(appointmentRepository.save(newAppointment));
    }

    @Override
    public AppointmentResponse createAppointment(AppointmentRequest appointmentRequest, UUID patientId) {
        if (!userRepository.existsById(patientId)) {
            throw new ProcessNotCompletedException("patient does not exist");
        }
        if (!clinicRepository.existsById(appointmentRequest.clinicId())) {
            throw new ProcessNotCompletedException("clinic does not exist");
        }
        Appointment appointment = appointmentMapper.toAppointment(appointmentRequest);
        User patient = User.builder().id(patientId).build();
        appointment.setPatient(patient);
        appointment.setStatus(AppointmentStatus.PENDING);
        return appointmentMapper.toResponse(appointmentRepository.save(appointment));
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
        return appointmentRepository.findByClinicClinicOwnerOwnerIdAndIssueDateAfterNowAndStatusNotAsParam(clinicId, LocalDateTime.now(), appointmentStatus)
                .stream()
                .map(appointmentMapper::toResponse)
                .toList();
    }

    @Override
    public boolean isAppointmentOwner(UUID appointmentId, UUID patientId) {
        log.info(appointmentId + "__" + patientId);
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ProcessNotCompletedException("Appointment not found"));
        return appointment.getPatient().getId().equals(patientId);
    }
}