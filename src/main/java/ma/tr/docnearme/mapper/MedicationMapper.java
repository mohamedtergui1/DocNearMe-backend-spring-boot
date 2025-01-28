package ma.tr.docnearme.mapper;


import ma.tr.docnearme.domain.entity.Medication;
import ma.tr.docnearme.dto.medication.MedicationRequest;
import ma.tr.docnearme.dto.medication.MedicationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicationMapper {
    MedicationResponse toResponse(Medication medication);

    Medication toMedication(MedicationRequest medicationRequest);
}
