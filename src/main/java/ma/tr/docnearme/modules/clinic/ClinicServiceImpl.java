package ma.tr.docnearme.modules.clinic;

import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.exception.NotFoundException;
import ma.tr.docnearme.exception.PermissionException;
import ma.tr.docnearme.exception.ProcessNotCompletedException;
import ma.tr.docnearme.modules.user.User;
import ma.tr.docnearme.modules.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ClinicServiceImpl implements ClinicService {

    private final ClinicRepository clinicRepository;
    private final ClinicMapper clinicMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public ClinicResponse getClinicById(UUID id) {
        return clinicMapper.clinicToClinicResponse(
                clinicRepository.findById(id)
                        .orElseThrow(() -> new NotFoundException("Clinic not found with ID: " + id))
        );
    }

    @Override
    public ClinicResponse addClinic(ClinicRequest clinicRequest, UUID clinicOwnerId) {
        if (clinicRepository.existsByClinicOwnerId(clinicOwnerId)) {
            throw new ProcessNotCompletedException("Clinic owner already has a registered clinic");
        }

        User owner = userRepository.findById(clinicOwnerId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + clinicOwnerId));

        Clinic newClinic = clinicMapper.clinicRequestToClinic(clinicRequest);
        newClinic.setClinicOwner(owner);

        Clinic savedClinic = clinicRepository.save(newClinic);
        return clinicMapper.clinicToClinicResponse(savedClinic);
    }

    @Override
    public ClinicResponse updateClinic(ClinicRequest clinicRequest, UUID id, UUID clinicOwnerId) {
        // 1. Fetch the existing clinic (throws NotFoundException if not found)
        Clinic existingClinic = clinicRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Clinic not found with ID: " + id));

        // 2. Verify the requesting user is the clinic owner (throws PermissionException if not)
        if (!existingClinic.getClinicOwner().getId().equals(clinicOwnerId)) {
            throw new PermissionException("User is not authorized to update this clinic");
        }

        // 3. Update only the allowed fields (avoid overwriting owner or ID)
        Clinic updatedClinic = clinicMapper.clinicRequestToClinic(clinicRequest);
        updatedClinic.setId(id); // Ensure the same ID is retained
        updatedClinic.setClinicOwner(existingClinic.getClinicOwner()); // Keep the original owner

        // 4. Save and return the updated clinic
        Clinic savedClinic = clinicRepository.save(updatedClinic);
        return clinicMapper.clinicToClinicResponse(savedClinic);
    }

    @Override
    public void deleteClinic(UUID id) {
        if (!clinicRepository.existsById(id)) {
            throw new NotFoundException("Clinic not found with ID: " + id);
        }
        clinicRepository.deleteById(id);
    }

    @Override
    public ClinicResponse findByClinicOwnerId(UUID id) {
        return clinicMapper.clinicToClinicResponse(clinicRepository.findByClinicOwnerId(id).orElseThrow(()-> new  NotFoundException("you should create  a clinic" )));
    }

    @Override
    public Page<ClinicResponse> findAllClinic(Pageable pageable) {
        return clinicRepository.findAll(pageable)
                .map(clinicMapper::clinicToClinicResponse);
    }

    @Override
    public Page<ClinicResponse> searchByNameAndFilterByCategoryId(String query, UUID categoryId, Pageable pageable) {
        query ="%"+ query +"%";
        return categoryId != null ? clinicRepository.findAllWhereNameContainsAndCAndCategoryId(query,categoryId,pageable).map(clinicMapper::clinicToClinicResponse)
                : clinicRepository.findAllWhereNameContains(query,pageable).map(clinicMapper::clinicToClinicResponse);
    }


}