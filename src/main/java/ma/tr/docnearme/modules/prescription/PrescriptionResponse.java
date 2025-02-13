package ma.tr.docnearme.modules.prescription;

import ma.tr.docnearme.modules.clinic.ClinicResponse;
import ma.tr.docnearme.modules.user.UserDtoResponse;

import java.util.UUID;

public record PrescriptionResponse(UUID id, UserDtoResponse patient , ClinicResponse clinic , PrescriptionStatus status) {

}