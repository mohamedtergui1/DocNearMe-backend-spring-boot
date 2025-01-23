package ma.tr.docnearme.dto.auth;

import ma.tr.docnearme.dto.user.UserDtoResponse;

public record LoginResponse(String accessToken
        , String refreshToken,
                            long refreshExpiresIn, UserDtoResponse user) {
}
