package ma.tr.docnearme.modules.medication;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ma.tr.docnearme.modules.consultation.Consultation;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Medication {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String instructions;

    @Column(nullable = false)
    private String ingredients;

    private String Forbidden;

}