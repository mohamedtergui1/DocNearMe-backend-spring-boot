package ma.tr.docnearme.modules.prescription;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.tr.docnearme.modules.clinic.Clinic;
import ma.tr.docnearme.modules.user.User;

import java.sql.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Prescription {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private User patient;

    @Temporal(TemporalType.DATE)
    private Date issueDate;

    @Enumerated(EnumType.STRING)
    private PrescriptionStatus status;

}