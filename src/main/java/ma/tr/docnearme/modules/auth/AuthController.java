package ma.tr.docnearme.modules.auth;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.util.dto.ApiResponse;
import ma.tr.docnearme.modules.user.UserDtoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/v1/auth/signup")
    public ResponseEntity<ApiResponse<UserDtoResponse>> register(@RequestBody @Valid RegisterRequest registerUserDto) {

        return ResponseEntity.ok(new ApiResponse<>("User registered successfully", authService.signup(registerUserDto)));
    }

    @PostMapping("/api/v1/auth/login")
    public ResponseEntity<ApiResponse<LoginResponse>> authenticate(@RequestBody @Valid LoginRequest loginUserDto) {

        return ResponseEntity.ok(new ApiResponse<>("User authenticated successfully", authService.authenticate(loginUserDto)));
    }

    @PostMapping("/api/v1/auth/verify")
    public ResponseEntity<ApiResponse<String>> verifyUser(@RequestBody @Valid VerifyUserDto verifyUserDto) {
        authService.verifyUser(verifyUserDto);
        return ResponseEntity.ok(new ApiResponse<>("Account verified successfully", null));
    }

    @PostMapping("/api/v1/auth/resend")
    public ResponseEntity<ApiResponse<String>> resendVerificationCode(@RequestBody sendVerificationCodeRequest resendVerificationCodeRequest) {
        authService.resendVerificationCode(resendVerificationCodeRequest);
        return ResponseEntity.ok(new ApiResponse<>("Verification code sent", null));
    }

    @PostMapping("/api/v1/auth/refresh")
    public ResponseEntity<ApiResponse<String>> refreshToken(@RequestBody sendVerificationCodeRequest resendVerificationCodeRequest) {
        authService.resendVerificationCode(resendVerificationCodeRequest);
        return ResponseEntity.ok(new ApiResponse<>("Verification code sent", null));
    }

    @GetMapping("/api/v1/authUser")
    public ResponseEntity<ApiResponse<UserDtoResponse>> getUser() {
        UserDtoResponse userDtoResponse = authService.authUser();
        return ResponseEntity.ok(new ApiResponse<>(null, userDtoResponse));
    }

}