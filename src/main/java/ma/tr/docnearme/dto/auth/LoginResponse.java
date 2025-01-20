package ma.tr.docnearme.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LoginResponse {
    private String token;
    private long expiresIn;
    private UserDtoResponse user;
}
