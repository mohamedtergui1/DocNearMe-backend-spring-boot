package ma.tr.docnearme.modules.consultation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.tr.docnearme.exception.NotFoundException;
import ma.tr.docnearme.exception.ProcessNotCompletedException;
import ma.tr.docnearme.modules.appointment.Appointment;
import ma.tr.docnearme.modules.appointment.AppointmentMapper;
import ma.tr.docnearme.modules.appointment.AppointmentRepository;
import ma.tr.docnearme.modules.appointment.AppointmentStatus;
import ma.tr.docnearme.modules.dosageschedule.MedicationDosageSchedule;
import ma.tr.docnearme.modules.dosageschedule.MedicationDosageScheduleRepository;
import ma.tr.docnearme.modules.user.User;
import ma.tr.docnearme.modules.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final UserRepository userRepository;
    private final ConsultationMapper consultationMapper;
    private final AppointmentRepository appointmentRepository;
    private final MedicationDosageScheduleRepository  medicationDosageScheduleRepository;

    @Override
    public ConsultationResponse getConsultation(UUID consultationId) {
        return consultationMapper.toResponse(consultationRepository.findById(consultationId).orElseThrow(() -> new NotFoundException("consultaion not found")));
    }

    @Override
    @Transactional
    public ConsultationResponse createConsultation(ConsultationRequest consultationRequest) {
        Appointment appointment =  appointmentRepository.findById(consultationRequest.appointmentId()).orElseThrow(()-> new ProcessNotCompletedException("no appointment found"));
        if(appointment.getIsCompleted()){
            throw new ProcessNotCompletedException("appointment already has been completed");
        }
        Consultation newConsultation = consultationMapper.toEntity(consultationRequest);
        newConsultation.setMedicalRecord(appointment.getPatient().getMedicalRecord());
        List<MedicationDosageSchedule> medicationDosageSchedules = newConsultation.getMedicationsDosageSchedule();
        newConsultation.setMedicationsDosageSchedule(new ArrayList<>());
        newConsultation = consultationRepository.save(newConsultation);
        Consultation finalNewConsultation = newConsultation;
        medicationDosageSchedules.forEach(medicationDosageSchedule -> {
            medicationDosageSchedule.setConsultation(finalNewConsultation);
        });
        medicationDosageScheduleRepository.saveAll(medicationDosageSchedules);
        finalNewConsultation.setMedicationsDosageSchedule(medicationDosageSchedules);
        appointment.setCompleted(true);
        appointmentRepository.save(appointment);
        return consultationMapper.toResponse(finalNewConsultation);
    }




    @Override
    public ConsultationResponse getConsultationByAppointmentId(UUID appointmentId) {
        return consultationMapper.toResponse(consultationRepository.findByAppointmentId(appointmentId).orElseThrow(()->  new NotFoundException("no consultation found")));
    }

    @Override
    public Page<ConsultationResponse> getConsultationsByMedicineId(Pageable pageable , UUID medicineId) {
        return consultationRepository.findByClinicClinicOwnerId(medicineId,pageable).map(consultationMapper::toResponse);
    }


}
