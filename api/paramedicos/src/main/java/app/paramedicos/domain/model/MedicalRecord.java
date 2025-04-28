package app.paramedicos.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "La razon de atención no puede estar vacia")
    private String attentionReason;

    @Column(name = "service_location")
    @NotBlank(message = "El lugar de servicio no puede estar vacio")
    private String serviceLocation;

    @Column(name = "vehicle_type")
    @NotBlank(message = "El tipo de vehiculo no puede estar vacio")
    private String vehicleType;

    @Column(name = "vehicle_num")
    @NotBlank(message = "El tipo de vehiculo no puede estar vacio")
    private String vehicleNum;

    @NotBlank(message = "El operador no puede estar vacio/a")
    private String operator;

    @NotBlank(message = "El interno no puede estar vacio/a")
    private String intern;

    @Column(name = "more_interns")
    private String moreInterns;

    @NotBlank(message = "La afiliación no puede estar vacio/a")
    private String affiliation;

    @NotBlank(message = "El genero no puede estar vacio/a")
    private String gender;

    private int age;

    @NotBlank(message = "La dirección no puede estar vacio/a")
    private String address;

    @NotBlank(message = "La colonia no puede estar vacio/a")
    private String colony;

    @NotBlank(message = "La municipalidad no puede estar vacio/a")
    private String municipality;

    @NotBlank(message = "El telefono no puede estar vacio/a")
    private String phone;

    @NotBlank(message = "El derechohabiente no puede estar vacio/a")
    private String rightful;

    @OneToOne(targetEntity = Patient.class)
    @JoinColumn(name = "patient_id", foreignKey = @ForeignKey(name = "fk_medicalrecord_patient"))
    private Patient patient;

    private LocalDateTime date;

}
