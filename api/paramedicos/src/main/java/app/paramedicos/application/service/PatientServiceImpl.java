package app.paramedicos.application.service;

import app.paramedicos.application.usecase.PatientService;
import app.paramedicos.domain.exception.PatientNotFoundException;
import app.paramedicos.domain.model.Patient;
import app.paramedicos.domain.repository.PatientRepository;
import app.paramedicos.web.dto.PatientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    @Transactional
    public Patient create() {

        LocalDate date = LocalDate.now();
        String folio;

        Patient patient = Patient.builder()
                .folio("")
                .completed(false)
                .build();

        // Save and get the patient with id
        Patient newPatient = patientRepository.save(patient);

        //update folio with date + id
        folio = date.toString().replaceAll("-", "")
                + newPatient.getId();

        newPatient.setFolio(folio);

        return patientRepository.save(newPatient);
    }

    @Override
    public List<PatientDto> showAllCompleted() {

        return patientRepository.findAllByCompletedTrue()
                .stream()
                .map(PatientDto::fromEntity)
                .toList();
    }

    @Override
    public List<PatientDto> showAllPending() {
        return patientRepository.findAllByCompletedFalse().stream()
                .map(PatientDto::fromEntity)
                .toList();
    }

    @Override
    public Patient findOne(Long id) {

        return patientRepository.findById(id)
                .orElseThrow(() -> new PatientNotFoundException("No se encontro paciente con ID: " + id));
    }

    @Override
    public Patient findOneByFolio(String folio) {
        return patientRepository.findByFolio(folio)
                .orElseThrow(() -> new PatientNotFoundException("No se encontro paciente con folio: " + folio));
    }

    @Override
    public Patient update(Patient patient, long id) {
        Patient dbPatient = findOne(id);

        patient.setId(dbPatient.getId());

        return patientRepository.save(patient);
    }

    @Override
    public void delete(long id) {
        if(!patientRepository.existsById(id)){
            throw new PatientNotFoundException("No se encontro paciente con ID: " + id);
        }

        patientRepository.deleteById(id);

    }
}
