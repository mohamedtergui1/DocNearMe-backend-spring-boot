package ma.tr.docnearme.service.auth;

import ma.tr.docnearme.dto.auth.*;
import ma.tr.docnearme.dto.user.UserDtoResponse;

public interface AuthService {
    UserDtoResponse signup(RegisterRequest input);
    UserDtoResponse authenticate(LoginRequest input);
    void verifyUser(VerifyUserDto input);
    void resendVerificationCode(sendVerificationCodeRequest sendVerificationCodeRequest);
    UserDtoResponse authUser();
}
