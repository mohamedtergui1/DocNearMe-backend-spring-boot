package ma.tr.docnearme.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ma.tr.docnearme.domain.enums.UserRole;

public record RegisterRequest(@NotBlank(message = "Password is required") @Size(min = 8, max = 30) String password,

                              @NotBlank(message = "Name is required") @Size(min = 8, max = 30) String name,

                              @NotBlank @Email String email,

                              @NotNull UserRole role) {
}
