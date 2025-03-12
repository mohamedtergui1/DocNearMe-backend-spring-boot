package ma.tr.docnearme.modules.appointment;

import lombok.RequiredArgsConstructor;
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
    public AppointmentResponse updateAppointment(AppointmentRequest appointmentRequest, UUID id, UUID patientId) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new ProcessNotCompletedException("no such appointment"));
        if (appointment.getStatus() != AppointmentStatus.PENDING) {
            throw new ProcessNotCompletedException("you can't update a valid pr rejected appointment");
        }
        if (!userRepository.existsById(patientId)) {
            throw new ProcessNotCompletedException("patient does not exist");
        }
        if (!clinicRepository.existsById(appointmentRequest.clinicId())) {
            throw new ProcessNotCompletedException("clinic does not exist");
        }
        Appointment newAppointment = appointmentMapper.toAppointment(appointmentRequest);
        newAppointment.setId(id);
        User patient = User.builder().id(patientId).build();
        newAppointment.setPatient(patient);
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
    public List<AppointmentResponse> getAppointmentsByClinicIdAfterNowAndByDeffrentStatus(UUID clinicId ,AppointmentStatus appointmentStatus) {
        return appointmentRepository.findByClinicClinicOwnerOwnerIdAndIssueDateAfterNowAndStatusNotAsParam(clinicId, LocalDateTime.now(), appointmentStatus).stream().map(appointmentMapper::toResponse).toList();
    }
}
