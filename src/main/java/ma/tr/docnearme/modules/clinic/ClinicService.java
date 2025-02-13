package ma.tr.docnearme.modules.clinic;

import java.util.UUID;

public interface ClinicService {
    ClinicResponse getClinicById(UUID id);
    ClinicResponse addClinic(ClinicRequest clinicRequest ,UUID clinicOwnerID);
    ClinicResponse updateClinic(ClinicRequest clinicRequest, UUID id , UUID clinicOwnerID);
    void deleteClinic(UUID id);
}
