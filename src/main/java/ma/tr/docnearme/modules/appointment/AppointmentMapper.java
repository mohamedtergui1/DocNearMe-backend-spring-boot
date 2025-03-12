package ma.tr.docnearme.modules.appointment;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "clinic.id", target = "clinicId")
    @Mapping(source = "status", target = "status")
    AppointmentResponse toResponse(Appointment appointment);

    @Mapping(source = "clinicId" , target = "clinic.id")
    Appointment toAppointment(AppointmentRequest appointmentRequest);
}
