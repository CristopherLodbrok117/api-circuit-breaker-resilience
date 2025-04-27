package app.paramedicos.infrastructure.confing;

import app.paramedicos.domain.model.MedicalRecord;
import app.paramedicos.domain.model.Patient;
import app.paramedicos.domain.repository.MedicalRecordRepository;
import app.paramedicos.domain.repository.PatientRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);
    private final PatientRepository patientRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    @Bean
    CommandLineRunner initDatabase(){
        return args -> {

            Patient p1 = Patient.builder()
                    .folio("007")
                    .completed(true)
                    .build();

            Patient p2 = Patient.builder()
                    .folio("117")
                    .completed(true)
                    .build();


            patientRepository.saveAll(
                    List.of(
                            p1,
                            p2,
                            Patient.builder()
                                    .folio("202504271")
                                    .completed(true)
                                    .build(),
                            Patient.builder()
                                    .folio("202504272")
                                    .completed(false)
                                    .build(),
                            Patient.builder()
                                    .folio("202504273")
                                    .completed(false)
                                    .build(),
                            Patient.builder()
                                    .folio("202504274")
                                    .completed(true)
                                    .build()
                    )
            );

            MedicalRecord record1 = MedicalRecord.builder()
                    .date(LocalDateTime.now())
                    .attentionReason("Andaba malito")
                    .serviceLocation("CUCEI")
                    .vehicleType("Cheyyene")
                    .vehicleNum("12-50-BAM")
                    .operator("Joaquin Loza")
                    .intern("El jericallo")
                    .moreInterns("Mario y Felix")
                    .affiliation("Mi tocayo")
                    .gender("El macho")
                    .age(24)
                    .address("Grieta del invocador")
                    .colony("Americana")
                    .municipality("Guadalajara")
                    .phone("33-18-9900")
                    .rightful("Teresa Garcia")
                    .patient(p1)
                    .build();

            MedicalRecord record2 = MedicalRecord.builder()
                    .date(LocalDateTime.now())
                    .attentionReason("Por agachon")
                    .serviceLocation("CUCEI")
                    .vehicleType("Lambo")
                    .vehicleNum("15-45-BUM")
                    .operator("Marquito")
                    .intern("El cebollo")
                    .moreInterns("Noe y Aurelio")
                    .affiliation("Su tocayo")
                    .gender("La caballera")
                    .age(24)
                    .address("Abismo de los almentos")
                    .colony("La jalisco")
                    .municipality("Guadalajara")
                    .phone("22-17-9933")
                    .rightful("Luis Alberto Mensoza Nuñez")
                    .patient(p2)
                    .build();

            medicalRecordRepository.saveAll(List.of(record1, record2));

            log.info("Registros insertados");
        };
    }

}
