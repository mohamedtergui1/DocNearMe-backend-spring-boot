package ma.tr.docnearme.modules.medication;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicationMapper {
    MedicationResponse toResponse(Medication medication);

    Medication toMedication(MedicationRequest medicationRequest);
}
