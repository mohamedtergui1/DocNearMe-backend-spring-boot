package ma.tr.docnearme.modules.clinic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface ClinicRepository extends CrudRepository<Clinic, UUID> {
    boolean existsByClinicOwnerId(UUID clinicOwnerId);
    Optional<Clinic> findByClinicOwnerId(UUID clinicOwnerId);

    Page<Clinic> findAll(Pageable pageable);
    @Query("SELECT c FROM Clinic c WHERE c.clinicName LIKE :name AND c.category.id = :categoryId")
    Page<Clinic> findAllWhereNameContainsAndCAndCategoryId(String name, UUID categoryId , Pageable pageable);

    @Query("SELECT c FROM Clinic c WHERE c.clinicName LIKE :name")
    Page<Clinic> findAllWhereNameContains(String name, Pageable pageable);
}
