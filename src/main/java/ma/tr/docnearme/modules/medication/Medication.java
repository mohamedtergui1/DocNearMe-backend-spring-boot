package ma.tr.docnearme.modules.medication;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "medications") // Explicitly define the table name
public class Medication {

    @Id
    @Column(name = "medication_id") // medicationId is a String and not auto-generated
    private String medicationId;

    @Column(name = "medication_name", nullable = false)
    private String medicationNameField;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private String distributor;

    @ElementCollection // Required for mapping a collection of basic types
    @CollectionTable(name = "medication_principles", joinColumns = @JoinColumn(name = "medication_id")) // Define the collection table
    @Column(name = "principle") // Define the column for the principles
    private List<String> principles;
}