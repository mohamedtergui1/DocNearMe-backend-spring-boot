package ma.tr.docnearme.dto.auth;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record VerifyUserDto(@Email @NotBlank String email,
                            @NotBlank @Size(min = 6, max = 6) String verificationCode) {
}