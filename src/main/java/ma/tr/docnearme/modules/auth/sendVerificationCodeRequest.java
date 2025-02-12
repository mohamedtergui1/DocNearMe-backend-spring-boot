package ma.tr.docnearme.modules.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record sendVerificationCodeRequest(@Email @NotBlank String email) {
}
