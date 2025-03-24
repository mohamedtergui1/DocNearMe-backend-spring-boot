package ma.tr.docnearme.modules.medication;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.tr.docnearme.exception.ProcessNotCompletedException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {
    private final MedicationRepository medicationRepository;
    private final OkHttpClient client;

    @Value("${medicationApi}")
    private String medicationApiUrl;

    @Override
    public MedicationResponse addMedication(MedicationRequest medicationRequest) {
        return null;
    }

    @Override
    public MedicationResponse updateMedication(MedicationRequest medicationRequest, String medicationId) {
        return null;
    }

    @Override
    public void deleteMedication(MedicationRequest medicationRequest) {
    }

    @Override
    public MedicationResponse getMedication(String medicationId) {
        return null;
    }

    @Override
    public List<MedicationResponse> getAllMedications() {
        return List.of();
    }

    @Override
    public List<MedicationResponse> searchMedications(String medicationName) {
        // Build the request to the API endpoint
        Request request = new Request.Builder()
                .url(medicationApiUrl + "/search?type=medicaments&word=" + medicationName)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                // Parse the response body as a JSON string
                String responseBody = response.body().string();

                // Parse the JSON response (using Jackson)
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode hitsNode = rootNode.path("data").path("all").path("hits").path("hits");

                // Create a list to hold the MedicationResponse objects
                List<MedicationResponse> medications = new ArrayList<>();

                // Loop through the hits and extract relevant fields
                for (JsonNode hit : hitsNode) {
                    JsonNode sourceNode = hit.path("_source");

                    String medicationId = sourceNode.path("mongoId").asText();
                    String medicationNameField = sourceNode.path("name").asText();
                    double price = sourceNode.path("ppv").asDouble();
                    String distributor = sourceNode.path("distributeuroufabriquant").asText();
                    List<String> principles = new ArrayList<>();

                    // Extract the principles (active ingredients)
                    JsonNode principlesNode = sourceNode.path("principes");
                    for (JsonNode principleNode : principlesNode) {
                        principles.add(principleNode.asText());
                    }

                    // Create a MedicationResponse object and add it to the list
                    MedicationResponse medicationResponse = new MedicationResponse(medicationId, medicationNameField, price, distributor, principles);
                    medications.add(medicationResponse);
                }


                synchronizeMedicationWithDatabase(medications);


                return medications;

            } else {
                throw new RuntimeException("Request failed with status code: " + response.code());
            }
        } catch (Exception e) {
            log.error("Error occurred while searching for medications: {}", e.getMessage(), e);
            throw new ProcessNotCompletedException("Error occurred: " + e.getMessage());
        }
    }

    @Async("taskExecutor")
    protected void synchronizeMedicationWithDatabase(List<MedicationResponse> medicationsResponse) {
        for (MedicationResponse medicationResponse : medicationsResponse) {
            try {

                Optional<Medication> existingMedication = medicationRepository.findById(medicationResponse.medicationId());

                if (existingMedication.isEmpty()) {

                    Medication newMedication = new Medication();
                    newMedication.setMedicationId(medicationResponse.medicationId());
                    newMedication.setMedicationNameField(medicationResponse.medicationNameField());
                    newMedication.setPrice(medicationResponse.price());
                    newMedication.setDistributor(medicationResponse.distributor());
                    newMedication.setPrinciples(medicationResponse.principles());


                    medicationRepository.save(newMedication);
                }
            } catch (Exception e) {
                // Log the error and continue processing the next medication
                log.error("Error occurred while synchronizing medication with ID {}: {}", medicationResponse.medicationId(), e.getMessage(), e);
            }
        }
    }
}