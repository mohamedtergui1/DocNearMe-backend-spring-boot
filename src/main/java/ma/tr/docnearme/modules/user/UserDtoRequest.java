package ma.tr.docnearme.modules.user;

import java.util.UUID;

public record UserDtoRequest(
        UUID id,
        String name,
        String email,
        UserRole role,
        String phoneNumber
) {
}
