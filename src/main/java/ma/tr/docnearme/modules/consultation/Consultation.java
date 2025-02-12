package ma.tr.docnearme.modules.consultation;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.tr.docnearme.modules.clinic.Clinic;
import ma.tr.docnearme.modules.medicalrecord.MedicalRecord;
import ma.tr.docnearme.modules.medication.Medication;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Consultation {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "medical_record_id")
    private MedicalRecord medicalRecord;

    private Date consultationDate;

    private String reason;

    private int recoveryDays;

    @Column(name = "watermark_path")
    private String watermarkPath;

    @OneToMany(mappedBy = "consultation")
    private List<Medication> medications;
}