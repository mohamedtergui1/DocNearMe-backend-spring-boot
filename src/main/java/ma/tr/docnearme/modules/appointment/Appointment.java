package ma.tr.docnearme.modules.appointment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.tr.docnearme.modules.clinic.Clinic;
import ma.tr.docnearme.modules.consultation.Consultation;
import ma.tr.docnearme.modules.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue
    private UUID id;

    private String subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clinic_id")
    private Clinic clinic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private User patient;

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    @Column(name = "is_completed")
    private boolean isCompleted;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;

    private LocalDateTime issueDate;

    @OneToOne(fetch = FetchType.LAZY,mappedBy = "appointment")
    private Consultation consultation;

    @PrePersist
    protected void onCreate() {
        issueDate = LocalDateTime.now();
    }

    public boolean getIsCompleted() {
        return isCompleted;
    }
}