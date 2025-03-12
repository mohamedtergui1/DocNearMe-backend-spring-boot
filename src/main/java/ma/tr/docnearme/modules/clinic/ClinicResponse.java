package ma.tr.docnearme.modules.clinic;
import ma.tr.docnearme.modules.user.UserDtoResponse;
import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

public record ClinicResponse(
        UUID id,

        String clinicName,

        UserDtoResponse clinicOwner,


        String clinicAddress,


        String categoryName,


        LocalTime startTime,


        LocalTime stopTime,


        Set<DayOfWeek> workingDays,

        Set<VacationPeriod> vacations
) {
}
