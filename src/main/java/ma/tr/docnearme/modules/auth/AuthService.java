package ma.tr.docnearme.modules.auth;

import ma.tr.docnearme.modules.user.UserDtoResponse;

public interface AuthService {
    UserDtoResponse signup(RegisterRequest input);
    LoginResponse authenticate(LoginRequest input);
    void verifyUser(VerifyUserDto input);
    void resendVerificationCode(sendVerificationCodeRequest sendVerificationCodeRequest);
    UserDtoResponse authUser();
}
