package ma.tr.docnearme.modules.dosageschedule;

import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.modules.user.User;
import ma.tr.docnearme.util.dto.ApiResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/medicationsdosages")
public class MedicationDosageScheduleController {
    private final MedicationDosageScheduleService medicationDosageScheduleService;

    @GetMapping("/getmedicationsdosagedimeforauthpatient")
    public ApiResponse<List<MedicationDosageScheduleResponse>> getMedicationsDosageTimeForAuthPatient() {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<MedicationDosageScheduleResponse> medicationDosageScheduleResponses = medicationDosageScheduleService.findByConsultationMedicalRecordPatientId(authUser.getId());
        return ApiResponse.<List<MedicationDosageScheduleResponse>>builder().data(medicationDosageScheduleResponses).build();
    }
}
