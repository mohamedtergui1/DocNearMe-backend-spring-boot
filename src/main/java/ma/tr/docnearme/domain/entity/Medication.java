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
public class Medication {
    @Id
    @GeneratedValue
    private UUID id;

    private String name; // "nom" → "name"

    private String dosage; // Already in English (no change needed)

    private String instructions; // Already in English (no change needed)

    @ManyToOne
    @JoinColumn(name = "prescription_id") // "ordonnance_id" → "prescription_id"
    private Prescription prescription; // Already in English (no change needed)
}