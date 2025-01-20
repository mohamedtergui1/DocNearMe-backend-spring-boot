package ma.tr.docnearme.domain.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Clinic {

    @Id
    @GeneratedValue
    private UUID id;

    private String phoneNumber;

    private String name;

    private String location;

    @OneToOne
    @JoinColumn(name = "doctor_id")
    private User doctor;

}
