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
public class DossierMedical {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @OneToMany(mappedBy = "dossierMedical")
    private List<Consultation> consultations;

    @OneToMany(mappedBy = "dossierMedical")
    private List<Ordonnance> ordonnances;

    @OneToMany(mappedBy = "dossierMedical")
    private List<ResultatExamen> resultatsExamens;
}
