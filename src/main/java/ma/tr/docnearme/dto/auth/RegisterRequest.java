package ma.tr.docnearme.dto.auth;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.ebankify.annotation.UniqueField;
import org.example.ebankify.entity.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 30)
    private String password;

    @NotBlank(message = "name is required")
    @Size(min = 8, max = 30)
    private String name;

    @UniqueField(entity = User.class, field = "email", message = "Email must be unique")
    @NotBlank(message = "Email is required")
    private String email;

    @Max(value = 80)
    @Min(value = 18)
    @NotNull
    private int age;

    @Min(value = 0)
    private Double monthlyIncome;

    @Min(value = 0)
    private int creditScore;


}
