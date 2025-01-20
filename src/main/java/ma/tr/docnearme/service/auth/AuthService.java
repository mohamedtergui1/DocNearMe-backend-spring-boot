package ma.tr.docnearme.service.auth;

import ma.tr.docnearme.dto.auth.*;

public interface AuthService {
    UserDtoResponse signup(RegisterRequest input);
    UserDtoResponse authenticate(LoginRequest input);
    void verifyUser(VerifyUserDto input);
    void resendVerificationCode(RsendVerificationCodeRequest rsendVerificationCodeRequest);
    UserDtoResponse authUser();
}
