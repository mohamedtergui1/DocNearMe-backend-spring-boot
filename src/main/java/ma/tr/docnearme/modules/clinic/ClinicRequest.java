package ma.tr.docnearme.modules.clinic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.Set;
import java.util.UUID;

public record ClinicRequest(
        @NotBlank
        String clinicName,

        @NotBlank
        String clinicAddress,

        @NotNull
        UUID categoryId,

        @NotNull
        LocalTime startTime,

        @NotNull
        LocalTime stopTime,

        @NotNull
        Set<DayOfWeek> workingDays,
        @NotNull
        Set<VacationPeriod> vacations
) {

}