package ma.tr.docnearme.modules.prescription;


import ma.tr.docnearme.modules.clinic.Clinic;
import ma.tr.docnearme.modules.clinic.ClinicResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrescriptionMapper {

    @Mapping(source = "clinic" , target = "clinic")
    @Mapping(source = "patient" , target = "patient")
    PrescriptionResponse toResponse(Prescription prescription);

    ClinicResponse map(Clinic clinic);

    @Mapping(source = "patientId" , target = "patient.id")
    @Mapping(source = "clinicId" , target = "clinic.id")
    Prescription toPrescription(PrescriptionRequest prescriptionRequest);
}
