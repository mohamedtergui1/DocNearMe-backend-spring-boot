package ma.tr.docnearme.modules.clinic;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ma.tr.docnearme.modules.category.Category;
import ma.tr.docnearme.modules.consultation.Consultation;
import ma.tr.docnearme.modules.appointment.Appointment;
import ma.tr.docnearme.modules.user.User;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalTime startTime;
    private LocalTime stopTime;

    @Column(name = "watermark_path")
    private String watermarkPath;


    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "clinic_working_days", joinColumns = @JoinColumn(name = "clinic_id"))
    private Set<DayOfWeek> workingDays;


    @ElementCollection
    @CollectionTable(name = "clinic_vacations", joinColumns = @JoinColumn(name = "clinic_id"))
    @Cascade({CascadeType.PERSIST, CascadeType.MERGE})
    private Set<VacationPeriod> vacations;

    @OneToMany(mappedBy = "clinic", cascade = jakarta.persistence.CascadeType.ALL)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "clinic", cascade = jakarta.persistence.CascadeType.ALL)
    private List<Consultation> consultations;

}