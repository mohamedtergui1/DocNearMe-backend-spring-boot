package ma.tr.docnearme.modules.user;

import jakarta.persistence.*;
import lombok.*;
import ma.tr.docnearme.modules.clinic.Clinic;
import ma.tr.docnearme.modules.medicalrecord.MedicalRecord;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String verificationCode;

    private LocalDateTime verificationCodeExpiresAt;

    private boolean enabled;

    private UserRole role;

    private String phoneNumber;


    @OneToOne(mappedBy = "patient")
    private MedicalRecord medicalRecord;

    @OneToOne(mappedBy = "clinicOwner")
    private Clinic clinic;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of( ()  -> "Role_" +  role.name());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isEnabled() {
       // return enabled;
        return true;
    }

}