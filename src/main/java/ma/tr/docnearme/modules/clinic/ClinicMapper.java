package ma.tr.docnearme.modules.clinic;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface ClinicMapper {

    // Map Clinic entity to ClinicResponse DTO
    @Mapping(source = "category.name", target = "categoryName")
    @Mapping(source = "workingDays", target = "workingDays", qualifiedByName = "mapWorkingDays")
    @Mapping(source = "vacations", target = "vacations", qualifiedByName = "mapVacations")
    ClinicResponse clinicToClinicResponse(Clinic clinic);

    // Map ClinicRequest DTO to Clinic entity
    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "workingDays", target = "workingDays", qualifiedByName = "mapWorkingDays")
    @Mapping(source = "vacations", target = "vacations", qualifiedByName = "mapVacations")
    Clinic clinicRequestToClinic(ClinicRequest clinicRequest);

    // Custom mapping method for Set<DayOfWeek>
    @Named("mapWorkingDays")
    default Set<DayOfWeek> mapWorkingDays(Set<DayOfWeek> workingDays) {
        if (workingDays == null) {
            return null;
        }
        return workingDays; // No transformation needed
    }

    // Custom mapping method for Set<VacationPeriod>
    @Named("mapVacations")
    default Set<VacationPeriod> mapVacations(Set<VacationPeriod> vacations) {
        if (vacations == null) {
            return null;
        }
        return vacations; // No transformation needed
    }
}