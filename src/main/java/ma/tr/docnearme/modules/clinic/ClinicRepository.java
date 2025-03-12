package ma.tr.docnearme.modules.clinic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClinicRepository extends CrudRepository<Clinic, UUID> {
    boolean existsByClinicOwnerId(UUID clinicOwnerId);
    Optional<Clinic> findByClinicOwnerId(UUID clinicOwnerId);

    Page<Clinic> findAll(Pageable pageable);
}
