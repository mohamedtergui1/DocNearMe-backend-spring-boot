package ma.tr.docnearme.mapper;


import ma.tr.docnearme.domain.entity.Prescription;
import ma.tr.docnearme.dto.prescription.PrescriptionRequest;
import ma.tr.docnearme.dto.prescription.PrescriptionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {
    PrescriptionResponse toResponse(Prescription prescription);

    Prescription toPrescription(PrescriptionRequest prescriptionRequest);
}
