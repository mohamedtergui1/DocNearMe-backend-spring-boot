package ma.tr.docnearme.dto.user;

import ma.tr.docnearme.domain.enums.UserRole;

import java.util.UUID;

public record UserDtoRequest(
        UUID id,
        String name,
        String email,
        UserRole role,
        String phoneNumber
) {
}
