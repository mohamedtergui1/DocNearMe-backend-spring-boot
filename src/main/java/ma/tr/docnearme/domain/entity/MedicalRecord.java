package ma.tr.docnearme.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class MedicalRecord {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @OneToMany(mappedBy = "medicalRecord")
    private List<Consultation> consultations;

    @OneToMany(mappedBy = "medicalRecord")
    private List<Prescription> prescriptions;

    
}