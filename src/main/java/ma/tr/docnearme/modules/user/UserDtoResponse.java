package ma.tr.docnearme.modules.user;

import java.util.UUID;

public record UserDtoResponse(
        UUID id,
        String name,
        String email,
        UserRole role,
        String phoneNumber
) {
}
