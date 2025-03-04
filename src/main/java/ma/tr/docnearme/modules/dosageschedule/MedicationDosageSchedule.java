package ma.tr.docnearme.modules.dosageschedule;

import jakarta.persistence.*;
import ma.tr.docnearme.modules.consultation.Consultation;
import ma.tr.docnearme.modules.medication.Medication;

@Entity
public class MedicationDosageSchedule {
    @Id
    @GeneratedValue
    private Long UUID;

    @Column(name = "number_of_days", nullable = false)
    private int numberOfDays;

    @ManyToOne
    @JoinColumn(name = "medication_id")
    private Medication medication;

    @ManyToOne
    @JoinColumn(name = "consultation_id")
    private Consultation consultation;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private String unit;

    @Column(name = "with_food")
    private boolean withFood;

    @Column(name = "special_instructions")
    private String specialInstructions;


}
