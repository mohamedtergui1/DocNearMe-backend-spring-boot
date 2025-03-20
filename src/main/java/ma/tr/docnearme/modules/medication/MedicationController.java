package ma.tr.docnearme.modules.medication;

import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.util.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medications")
@RequiredArgsConstructor
public class MedicationController {
    public final MedicationService medicationService;

    @GetMapping("/search-medications")
    public ApiResponse<List<MedicationResponse>> searchMedications(@RequestParam(required = false ,defaultValue = "") String query) {

        query = query.toLowerCase();
        List<MedicationResponse> medications = medicationService.searchMedications(query);

        return ApiResponse.<List<MedicationResponse>>builder().data(medications).build();
    }
}
