package app.paramedicos.web.controller;

import app.paramedicos.application.service.PatientServiceImpl;
import app.paramedicos.application.usecase.PatientService;
import app.paramedicos.domain.model.Patient;
import app.paramedicos.web.dto.PatientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientServiceImpl patientService;

    @GetMapping
    public ResponseEntity<List<PatientDto>> showAll(@RequestParam(name = "completed") boolean completed){
        if(completed){
            return ResponseEntity.ok(patientService.showAllCompleted());
        }

        return ResponseEntity.ok(patientService.showAllPending());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> findOne(@PathVariable Long id){
        return ResponseEntity.ok(patientService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<Patient> create(@RequestBody PatientDto patientDto
            , UriComponentsBuilder ucb){
        Patient newPatient = patientService.create();

        URI patientLocation = ucb
                .path("/api/patients/{id}")
                .buildAndExpand(newPatient.getId())
                .toUri();

        return ResponseEntity.created(patientLocation).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> update(@PathVariable Long id, @RequestBody Patient patient){

        return ResponseEntity.ok(patientService.update(patient, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        patientService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
