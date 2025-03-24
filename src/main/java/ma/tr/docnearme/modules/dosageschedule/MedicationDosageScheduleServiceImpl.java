package ma.tr.docnearme.modules.dosageschedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicationDosageScheduleServiceImpl implements MedicationDosageScheduleService {
    private final MedicationDosageScheduleRepository medicationDosageScheduleRepository;
    private final MedicationDosageScheduleMapper medicationDosageScheduleMapper;
    @Override
    public List<MedicationDosageScheduleResponse> findByConsultationMedicalRecordPatientId(UUID id) {
        return medicationDosageScheduleRepository.findByConsultationMedicalRecordPatientId(id).stream().map(medicationDosageScheduleMapper::toResponse).toList();
    }
}
