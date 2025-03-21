package ma.tr.docnearme.modules.user;

import java.util.UUID;

public record UserDtoRequest(
        String name,
        String email,
        String password,
        UserRole role,
        String phoneNumber
) {
}
