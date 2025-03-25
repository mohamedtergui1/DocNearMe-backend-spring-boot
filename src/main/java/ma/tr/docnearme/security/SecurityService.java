package ma.tr.docnearme.security;

import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.exception.NotFoundException;
import ma.tr.docnearme.exception.ProcessNotCompletedException;
import ma.tr.docnearme.modules.appointment.Appointment;
import ma.tr.docnearme.modules.appointment.AppointmentRepository;
import ma.tr.docnearme.modules.appointment.AppointmentStatus;
import ma.tr.docnearme.modules.consultation.ConsultationRepository;
import ma.tr.docnearme.modules.user.User;
import ma.tr.docnearme.modules.user.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final ConsultationRepository consultationRepository;

    public boolean checkIfCanCanOpenTheConsultation(UUID AppointmentId, UUID authUserID) {
        Appointment appointment = appointmentRepository.findById(AppointmentId).orElseThrow(() -> new NotFoundException("the consultation not found"));
        User authUser = userRepository.findById(authUserID).orElseThrow(() -> new AccessDeniedException("you can't access to this route"));
        return authUser.getId().equals(appointment.getClinic().getClinicOwner().getId()) || authUser.getId().equals(appointment.getPatient().getId());
    }

    public boolean checkIfCanCreateConsultation(UUID AppointmentId, UUID authUserID) {
        User authUser = userRepository.findById(authUserID).orElseThrow(() -> new AccessDeniedException("you can't access to this route"));
        Appointment appointment = appointmentRepository.findById(AppointmentId).orElseThrow(() -> new AccessDeniedException("you can't access to this route"));
        return appointment.getStatus() == AppointmentStatus.VALID && authUser.getClinic().getId().equals(appointment.getClinic().getId());
    }
    public boolean isAppointmentOwner(UUID appointmentId, UUID patientId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ProcessNotCompletedException("Appointment not found"));
        return appointment.getPatient().getId().equals(patientId);
    }

    public boolean isAppointmentClinicOwner(UUID appointmentId, UUID medicineId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ProcessNotCompletedException("Appointment not found"));
        return appointment.getClinic().getClinicOwner().getId().equals(medicineId);
    }
}
