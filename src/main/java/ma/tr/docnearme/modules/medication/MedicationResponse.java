package ma.tr.docnearme.modules.medication;

import java.util.List;

public record MedicationResponse(
        String medicationId, String medicationNameField, double price, String distributor, List<String> principles
) {

}
