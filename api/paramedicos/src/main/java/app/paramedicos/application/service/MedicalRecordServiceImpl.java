package app.paramedicos.application.service;

import app.paramedicos.application.usecase.MedicalRecordService;
import app.paramedicos.domain.exception.MedicalRecordException;
import app.paramedicos.domain.model.MedicalRecord;
import app.paramedicos.domain.repository.MedicalRecordRepository;
import app.paramedicos.web.dto.MedicalRecordDto;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final CircuitBreaker medicalRecordCircuitBreaker;
    private final Validator validator;

    @Override
    public MedicalRecord create(MedicalRecord medicalRecord) {
        return medicalRecordCircuitBreaker.executeSupplier(() ->{
            validateMedicalRecord( medicalRecord);
            return medicalRecordRepository.save(medicalRecord);
        });
    }

    @Override
    public List<MedicalRecordDto> showAll() {
        return medicalRecordCircuitBreaker.executeSupplier(() ->
                medicalRecordRepository.findAll().stream()
                        .map(MedicalRecordDto::fromEntity)
                        .toList()
        );
    }

    @Override
    public MedicalRecord findOne(Long id) {
        return medicalRecordCircuitBreaker.executeSupplier(() ->
                medicalRecordRepository.findById(id)
                        .orElseThrow(() -> new MedicalRecordException("No se encontro el record con ID: " + id))
        );

    }

    @Override
    public MedicalRecord findOneByPatient(Long id) {
        return medicalRecordCircuitBreaker.executeSupplier(() ->
                medicalRecordRepository.findByPatient_Id(id)
                        .orElseThrow(() -> new MedicalRecordException("No se encontro el record del paciente con ID: " + id))
        );


    }

    @Override
    public MedicalRecord update(MedicalRecord medicalRecord, long id) {
        return medicalRecordCircuitBreaker.executeSupplier(() ->{
            validateMedicalRecord(medicalRecord);

            MedicalRecord dbMedicalRecord = findOne(id);

            medicalRecord.setId(dbMedicalRecord.getId());

            return medicalRecordRepository.save(medicalRecord);
        });


    }

    @Override
    public void delete(long id) {
         medicalRecordCircuitBreaker.executeRunnable(() ->{
            if(!medicalRecordRepository.existsById(id)){
                throw new MedicalRecordException("No se encontro el record con ID: " + id);
            }

            medicalRecordRepository.deleteById(id);
        });

    }

    @Override
    public void validateMedicalRecord(MedicalRecord record) {
        Set<ConstraintViolation<MedicalRecord>> violations = validator.validate(record);
        if (!violations.isEmpty()) {
            throw new ValidationException("Error de validación: " + violations.iterator().next().getMessage());
        }


    }
}
