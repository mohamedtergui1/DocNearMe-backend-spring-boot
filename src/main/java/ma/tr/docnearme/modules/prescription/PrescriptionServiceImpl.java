package ma.tr.docnearme.modules.prescription;

import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.exception.ProcessNotCompletedException;
import ma.tr.docnearme.modules.clinic.ClinicRepository;
import ma.tr.docnearme.modules.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionMapper prescriptionMapper;
    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;

    @Override
    public PrescriptionResponse getPrescription(UUID id) {
        return prescriptionMapper.toResponse(prescriptionRepository.findById(id).orElseThrow(() -> new ProcessNotCompletedException(id.toString())));
    }

    @Override
    public PrescriptionResponse updatePrescription(PrescriptionRequest prescriptionRequest, UUID id) {
        Prescription prescription = prescriptionRepository.findById(id).orElseThrow(() -> new ProcessNotCompletedException("no such prescription"));
        if (prescription.getStatus() != PrescriptionStatus.PENDING) {
            throw new ProcessNotCompletedException("you can't update a valid pr rejected prescription");
        }
        if (!userRepository.existsById(prescriptionRequest.patientId())) {
            throw new ProcessNotCompletedException("patient does not exist");
        }
        if (!clinicRepository.existsById(prescriptionRequest.clinicId())) {
            throw new ProcessNotCompletedException("clinic does not exist");
        }
        Prescription newPrescription = prescriptionMapper.toPrescription(prescriptionRequest);
        newPrescription.setId(id);
        return prescriptionMapper.toResponse(prescriptionRepository.save(newPrescription));
    }

    @Override
    public PrescriptionResponse createPrescription(PrescriptionRequest prescriptionRequest) {
        if (!userRepository.existsById(prescriptionRequest.patientId())) {
            throw new ProcessNotCompletedException("patient does not exist");
        }
        if (!clinicRepository.existsById(prescriptionRequest.clinicId())) {
            throw new ProcessNotCompletedException("clinic does not exist");
        }
        Prescription prescription = prescriptionMapper.toPrescription(prescriptionRequest);
        return prescriptionMapper.toResponse(prescriptionRepository.save(prescription));
    }

    @Override
    public void deletePrescription(UUID id) {
        Prescription prescription = prescriptionRepository.findById(id).orElseThrow(() -> new ProcessNotCompletedException("no such prescription"));
        if (prescription.getStatus() == PrescriptionStatus.VALID) {
            throw new ProcessNotCompletedException("you can't delete a valid prescription");
        }
        prescriptionRepository.deleteById(id);
    }
}
