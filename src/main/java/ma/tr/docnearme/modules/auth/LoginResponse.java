package ma.tr.docnearme.modules.auth;

import ma.tr.docnearme.modules.user.UserDtoResponse;

public record LoginResponse(String accessToken
        , String refreshToken,
                            long refreshExpiresIn, UserDtoResponse user) {
}
