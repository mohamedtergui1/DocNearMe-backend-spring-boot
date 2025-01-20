package ma.tr.docnearme.dto.auth;

import ma.tr.docnearme.dto.user.UserDtoResponse;

public record LoginResponse(String token, long expiresIn, UserDtoResponse user) {
}
