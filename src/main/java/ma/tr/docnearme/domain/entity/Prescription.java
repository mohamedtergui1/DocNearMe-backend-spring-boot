package ma.tr.docnearme.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Prescription {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "doctor_id") // "medecin_id" → "doctor_id"
    private User doctor; // "medecin" → "doctor"

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @Temporal(TemporalType.DATE)
    private Date issueDate; // "dateEmission" → "issueDate"

    @OneToMany(mappedBy = "prescription")
    private List<Medication> medications; // "medicaments" → "medications"

    @ManyToOne
    @JoinColumn(name = "medical_record_id") // "dossierMedical_id" → "medical_record_id"
    private MedicalRecord medicalRecord;
}