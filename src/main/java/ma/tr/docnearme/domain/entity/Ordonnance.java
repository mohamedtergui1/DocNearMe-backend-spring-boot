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
public class Ordonnance {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private User medecin;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @Temporal(TemporalType.DATE)
    private Date dateEmission;

    @OneToMany(mappedBy = "ordonnance")
    private List<Medicament> medicaments;

    @ManyToOne
    @JoinColumn(name = "dossierMedical_id")
    private DossierMedical dossierMedical;

}