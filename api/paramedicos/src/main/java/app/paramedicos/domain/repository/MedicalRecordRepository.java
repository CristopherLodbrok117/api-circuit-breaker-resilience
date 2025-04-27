package app.paramedicos.domain.repository;

import app.paramedicos.domain.model.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
    boolean existsByPatient_Id(Long id);
    Optional<MedicalRecord> findByPatient_Id(Long id);
}
