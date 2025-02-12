package ma.tr.docnearme.modules.clinic;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ClinicRepository extends CrudRepository<Clinic, UUID> {
    boolean existsByClinicOwnerId(UUID clinicOwnerId);
}
