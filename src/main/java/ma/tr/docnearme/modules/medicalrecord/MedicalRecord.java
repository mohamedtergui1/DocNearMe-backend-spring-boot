package ma.tr.docnearme.modules.medicalrecord;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ma.tr.docnearme.modules.user.User;
import ma.tr.docnearme.modules.consultation.Consultation;

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

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private User patient;

    @OneToMany(mappedBy = "medicalRecord")
    private List<Consultation> consultations;
    
}