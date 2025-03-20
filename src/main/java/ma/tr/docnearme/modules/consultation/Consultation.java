package ma.tr.docnearme.modules.consultation;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.tr.docnearme.modules.appointment.Appointment;
import ma.tr.docnearme.modules.clinic.Clinic;
import ma.tr.docnearme.modules.dosageschedule.MedicationDosageSchedule;
import ma.tr.docnearme.modules.medicalrecord.MedicalRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Consultation {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id")
    @NotNull
    private Clinic clinic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medical_record_id")
    @NotNull
    private MedicalRecord medicalRecord;

    @Size(max = 500)
    private String reason;

    private int recoveryDays;


    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MedicationDosageSchedule> medicationsDosageSchedule = new ArrayList<>();

    private LocalDateTime consultationDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @PrePersist
    public void prePersist(){
        consultationDate = LocalDateTime.now();
    }
}