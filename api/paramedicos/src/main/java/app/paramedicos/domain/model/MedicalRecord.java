package app.paramedicos.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attention_reason")
    private String attentionReason;

    @Column(name = "service_location")
    private String serviceLocation;

    @Column(name = "vehicle_type")
    private String vehicleType;

    @Column(name = "vehicle_num")
    private String vehicleNum;
    private String operator;
    private String intern;

    @Column(name = "more_interns")
    private String moreInterns;
    private String affiliation;
    private String gender;
    private int age;
    private String address;
    private String colony;
    private String municipality;
    private String phone;
    private String rightful;

    @OneToOne(targetEntity = Patient.class)
    @JoinColumn(name = "patient_id", foreignKey = @ForeignKey(name = "fk_medicalrecord_patient"))
    private Patient patient;

    private LocalDateTime date;

}
