package ma.tr.docnearme.modules.consultation;

import ma.tr.docnearme.modules.dosageschedule.MedicationDosageSchedule;
import ma.tr.docnearme.modules.dosageschedule.MedicationDosageScheduleRequest;
import ma.tr.docnearme.modules.medication.Medication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {

    @Mapping(source = "clinicId", target = "clinic.id")
    @Mapping(source = "medicationsDosageSchedule", target = "medicationsDosageSchedule", qualifiedByName = "mapDosageSchedules")
    @Mapping(source = "appointmentId" , target = "appointment.id")
    Consultation toEntity(ConsultationRequest consultationRequest);

    @Mapping(source = "appointment.id", target = "appointmentId")
    @Mapping(source = "medicationsDosageSchedule", target = "medicationsDosageSchedule")
    ConsultationResponse toResponse(Consultation consultation);

    @Named("mapDosageSchedules")
    default List<MedicationDosageSchedule> mapDosageSchedules(List<MedicationDosageScheduleRequest> dosageRequests) {
        if (dosageRequests == null) {
            return null;
        }
        return dosageRequests.stream()
                .map(this::mapDosageSchedule)
                .collect(Collectors.toList());
    }

    default MedicationDosageSchedule mapDosageSchedule(MedicationDosageScheduleRequest dosageRequest) {
        MedicationDosageSchedule dosageSchedule = new MedicationDosageSchedule();
        dosageSchedule.setDateWhenMustStopConsumption(dosageRequest.dateWhenMustStopConsumption());
        dosageSchedule.setNumberOfConsumptionInDay(dosageRequest.numberOfConsumptionInDay());
        dosageSchedule.setQuantity(dosageRequest.quantity());
        dosageSchedule.setSpecialInstructions(dosageRequest.specialInstructions());
        dosageSchedule.setUnit(dosageRequest.unit());
        dosageSchedule.setWithFood(dosageRequest.withFood());

        Medication medication = new Medication();
        medication.setMedicationId(dosageRequest.medicationId());
        dosageSchedule.setMedication(medication);

        return dosageSchedule;
    }
}