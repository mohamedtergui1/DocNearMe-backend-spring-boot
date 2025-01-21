package ma.tr.docnearme.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Medicament {
    @Id
    @GeneratedValue
    private UUID id;

    private String nom;

    private String dosage;

    private String instructions;

    @ManyToOne
    @JoinColumn(name = "ordonnance_id")
    private Ordonnance ordonnance;
}
