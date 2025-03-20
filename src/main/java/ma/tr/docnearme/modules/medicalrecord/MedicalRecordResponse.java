package ma.tr.docnearme.modules.medicalrecord;


import ma.tr.docnearme.modules.user.UserDtoResponse;

import java.util.UUID;

public record MedicalRecordResponse(
        UUID id,
        UserDtoResponse patient
) {
}
