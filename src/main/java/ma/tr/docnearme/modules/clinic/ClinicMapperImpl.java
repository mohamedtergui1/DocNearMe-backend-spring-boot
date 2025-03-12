//package ma.tr.docnearme.modules.clinic;
//
//import ma.tr.docnearme.modules.category.Category;
//import org.springframework.stereotype.Component;
//
//import java.util.Set;
//
//@Component // Make this class a Spring bean
//public class ClinicMapperImpl {
//
//    // Map Clinic entity to ClinicResponse DTO
//    public ClinicResponse clinicToClinicResponse(Clinic clinic) {
//        if (clinic == null) {
//            return null;
//        }
//
//        return new ClinicResponse(
//                clinic.getId(),
//                clinic.getClinicName(),
//                mapUserToUserDtoResponse(clinic.getClinicOwner()),
//                clinic.getClinicAddress(),
//                clinic.getCategory() != null ? clinic.getCategory().getName() : null,
//                clinic.getStartTime(),
//                clinic.getStopTime(),
//                mapWorkingDays(clinic.getWorkingDays()),
//                mapVacations(clinic.getVacations())
//        );
//    }
//
//    // Map ClinicRequest DTO to Clinic entity
//    public Clinic clinicRequestToClinic(ClinicRequest clinicRequest) {
//        if (clinicRequest == null) {
//            return null;
//        }
//
//        Clinic clinic = new Clinic();
//        clinic.setClinicName(clinicRequest.clinicName());
//        clinic.setClinicAddress(clinicRequest.clinicAddress());
//
//        // Set category (assuming you have a Category entity with an ID)
//        Category category = new Category();
//        category.setId(clinicRequest.categoryId());
//        clinic.setCategory(category);
//
//        clinic.setStartTime(clinicRequest.startTime());
//        clinic.setStopTime(clinicRequest.stopTime());
//        clinic.setWorkingDays(mapWorkingDays(clinicRequest.workingDays()));
//        clinic.setVacations(mapVacations(clinicRequest.vacations()));
//
//        return clinic;
//    }
//
//    // Custom mapping method for Set<DayOfWeek>
//    private Set<DayOfWeek> mapWorkingDays(Set<DayOfWeek> workingDays) {
//        if (workingDays == null) {
//            return null;
//        }
//        return workingDays; // No transformation needed
//    }
//
//    // Custom mapping method for Set<VacationPeriod>
//    private Set<VacationPeriod> mapVacations(Set<VacationPeriod> vacations) {
//        if (vacations == null) {
//            return null;
//        }
//        return vacations; // No transformation needed
//    }
//
//    // Custom mapping method for User to UserDtoResponse
//    private UserDtoResponse mapUserToUserDtoResponse(User user) {
//        if (user == null) {
//            return null;
//        }
//        return new UserDtoResponse(
//                user.getId(),
//                user.getName(),
//                user.getEmail()
//        );
//    }
//}