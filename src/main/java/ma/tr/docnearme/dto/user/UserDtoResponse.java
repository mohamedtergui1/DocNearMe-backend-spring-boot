package ma.tr.docnearme.dto.user;

import ma.tr.docnearme.domain.enums.UserRole;

import java.util.UUID;

public record UserDtoResponse(
        UUID id,
        String email,
        UserRole role,
        String phoneNumber
) {
}
