package ma.tr.docnearme.modules.clinic;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClinicMapper {
    @Mapping(source = "category.name" , target = "categoryName")
    @Mapping(source = "clinicOwner.name" , target = "clinicOwnerName")
    ClinicResponse clinicToClinicResponse(Clinic clinic);

    @Mapping(source = "categoryId", target = "category.id")
    Clinic clinicRquestToClinic(ClinicRequest clinicRequest);
}
