package ma.tr.docnearme.modules.medication;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ma.tr.docnearme.modules.consultation.Consultation;
import ma.tr.docnearme.modules.prescription.Prescription;

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

    private String dosage;

    private String instructions;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;
}