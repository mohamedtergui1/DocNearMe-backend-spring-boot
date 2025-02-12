package ma.tr.docnearme.modules.prescription;

import ma.tr.docnearme.modules.clinic.ClinicResponse;
import ma.tr.docnearme.modules.user.UserDtoResponse;

public record PrescriptionResponse(UserDtoResponse patient ,ClinicResponse clinic ,PrescriptionStatus status) {

}