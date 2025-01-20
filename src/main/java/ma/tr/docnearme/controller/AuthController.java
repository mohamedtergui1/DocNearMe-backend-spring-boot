package ma.tr.docnearme.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.tr.docnearme.dto.ApiResponse;
import ma.tr.docnearme.dto.auth.*;
import ma.tr.docnearme.dto.user.UserDtoResponse;
import ma.tr.docnearme.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<ApiResponse<UserDtoResponse>> register(@RequestBody @Valid RegisterRequest registerUserDto) {

        return ResponseEntity.ok(new ApiResponse<>("User registered successfully", authService.signup(registerUserDto)));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<UserDtoResponse>> authenticate(@RequestBody @Valid LoginRequest loginUserDto) {

        return ResponseEntity.ok(new ApiResponse<>("User authenticated successfully", authService.authenticate(loginUserDto)));
    }

    @PostMapping("/auth/verify")
    public ResponseEntity<ApiResponse<String>> verifyUser(@RequestBody @Valid VerifyUserDto verifyUserDto) {
        authService.verifyUser(verifyUserDto);
        return ResponseEntity.ok(new ApiResponse<>("Account verified successfully", null));
    }

    @PostMapping("/auth/resend")
    public ResponseEntity<ApiResponse<String>> resendVerificationCode(@RequestBody sendVerificationCodeRequest resendVerificationCodeRequest) {
        authService.resendVerificationCode(resendVerificationCodeRequest);
        return ResponseEntity.ok(new ApiResponse<>("Verification code sent", null));
    }

    @GetMapping("/authUser")
    public ResponseEntity<ApiResponse<UserDtoResponse>> getUser() {
        UserDtoResponse userDtoResponse = authService.authUser();
        return ResponseEntity.ok(new ApiResponse<>("User fetched successfully", userDtoResponse));
    }

}