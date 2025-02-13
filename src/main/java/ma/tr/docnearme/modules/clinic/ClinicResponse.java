package ma.tr.docnearme.modules.clinic;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import ma.tr.docnearme.modules.category.Category;
import ma.tr.docnearme.modules.user.User;

import java.util.UUID;

public record ClinicResponse(
        UUID id,
        String clinicOwnerName,
        String clinicName,
        String clinicAddress,
        String categoryName
) {
}
