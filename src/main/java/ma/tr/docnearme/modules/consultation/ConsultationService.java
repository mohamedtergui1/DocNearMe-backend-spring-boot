package ma.tr.docnearme.modules.consultation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ConsultationService {
    ConsultationResponse getConsultation(UUID consultationId);

    ConsultationResponse createConsultation(ConsultationRequest consultationRequest);


    ConsultationResponse getConsultationByAppointmentId(UUID appointmentId);

    Page<ConsultationResponse> getConsultationsByMedicineId(Pageable pageable , UUID medicineId);
}
