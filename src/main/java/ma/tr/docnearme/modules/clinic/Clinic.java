package ma.tr.docnearme.modules.clinic;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.tr.docnearme.modules.category.Category;
import ma.tr.docnearme.modules.consultation.Consultation;
import ma.tr.docnearme.modules.prescription.Prescription;
import ma.tr.docnearme.modules.user.User;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Clinic {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private User clinicOwner;
    private String clinicName;
    private String clinicAddress;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "clinic")
    private List<Prescription> prescriptions;

    @OneToMany(mappedBy = "clinic")
    private List<Consultation> consultations;
}
