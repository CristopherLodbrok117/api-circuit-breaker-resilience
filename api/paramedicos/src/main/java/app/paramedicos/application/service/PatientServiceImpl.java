package app.paramedicos.application.service;

import app.paramedicos.application.usecase.PatientService;
import app.paramedicos.domain.exception.PatientNotFoundException;
import app.paramedicos.domain.model.Patient;
import app.paramedicos.domain.repository.PatientRepository;
import app.paramedicos.web.dto.PatientDto;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {


    private final PatientRepository patientRepository;
    private final CircuitBreaker patientServiceCircuitBreaker;

    @Override
    @Transactional
    public Patient create() {
        return patientServiceCircuitBreaker.executeSupplier(() -> {
            LocalDate date = LocalDate.now();
            String folio;

            Patient patient = Patient.builder()
                    .folio("")
                    .completed(false)
                    .build();

            Patient newPatient = patientRepository.save(patient);

            folio = date.toString().replaceAll("-", "") + newPatient.getId();
            newPatient.setFolio(folio);

            return patientRepository.save(newPatient);
        });
    }

    @Override
    public List<PatientDto> showAllCompleted() {
        return patientServiceCircuitBreaker.executeSupplier(() ->
                patientRepository.findAllByCompletedTrue()
                        .stream()
                        .map(PatientDto::fromEntity)
                        .toList()
        );
    }

    @Override
    public List<PatientDto> showAllPending() {
        return patientServiceCircuitBreaker.executeSupplier(() ->
                patientRepository.findAllByCompletedFalse()
                        .stream()
                        .map(PatientDto::fromEntity)
                        .toList()
        );
    }

    @Override
    public Patient findOne(Long id) {
        return patientServiceCircuitBreaker.executeSupplier(() ->
                patientRepository.findById(id)
                        .orElseThrow(() -> new PatientNotFoundException("No se encontró paciente con ID: " + id))
        );
    }

    @Override
    public Patient findOneByFolio(String folio) {
        return patientServiceCircuitBreaker.executeSupplier(() ->
                patientRepository.findByFolio(folio)
                        .orElseThrow(() -> new PatientNotFoundException("No se encontró paciente con folio: " + folio))
        );
    }

    @Override
    public Patient update(Patient patient, long id) {
        return patientServiceCircuitBreaker.executeSupplier(() -> {
            Patient dbPatient = findOne(id);
            patient.setId(dbPatient.getId());
            return patientRepository.save(patient);
        });
    }

    @Override
    public void delete(long id) {
        patientServiceCircuitBreaker.executeRunnable(() -> {
            if (!patientRepository.existsById(id)) {
                throw new PatientNotFoundException("No se encontró paciente con ID: " + id);
            }
            patientRepository.deleteById(id);
        });
    }
}
