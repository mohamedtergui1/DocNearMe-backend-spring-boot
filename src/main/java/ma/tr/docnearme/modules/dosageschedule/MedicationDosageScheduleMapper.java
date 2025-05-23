package ma.tr.docnearme.modules.dosageschedule;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MedicationDosageScheduleMapper {
    @Mapping(source = "medicationId" , target = "medication.medicationId")
    MedicationDosageSchedule toEntity(MedicationDosageScheduleRequest medicationDosageScheduleRequest);

    @Mapping( source = "consultation.id" , target = "consultationId" )
    @Mapping(source = "consultation.consultationDate",target = "consultationDate")
    MedicationDosageScheduleResponse toResponse(MedicationDosageSchedule medicationDosageSchedule);
}
