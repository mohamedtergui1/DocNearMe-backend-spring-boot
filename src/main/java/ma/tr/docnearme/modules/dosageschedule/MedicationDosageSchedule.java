package ma.tr.docnearme.modules.dosageschedule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.tr.docnearme.modules.consultation.Consultation;
import ma.tr.docnearme.modules.medication.Medication;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedicationDosageSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "number_of_consumption_in_day", nullable = false)
    private int numberOfConsumptionInDay;

    @Column(name = "date_when_must_stop_consumption", nullable = false)
    private LocalDate dateWhenMustStopConsumption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultation_id", nullable = false)
    private Consultation consultation;

    @Column(nullable = false)
    private Double quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Unit unit;

    @Column(name = "with_food", nullable = false)
    private boolean withFood;

    @Column(name = "special_instructions", columnDefinition = "TEXT")
    private String specialInstructions;
}