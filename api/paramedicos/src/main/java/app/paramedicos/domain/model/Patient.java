package app.paramedicos.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String folio;

    private boolean completed; // Completed 0 - pending 1

//    @OneToOne(targetEntity = MedicalRecord.class, mappedBy = "patient", fetch = FetchType.LAZY)
//    MedicalRecord medicalRecord;
}
