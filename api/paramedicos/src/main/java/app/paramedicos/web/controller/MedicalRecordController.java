package app.paramedicos.web.controller;

import app.paramedicos.application.usecase.MedicalRecordService;
import app.paramedicos.domain.model.MedicalRecord;
import app.paramedicos.domain.model.Patient;
import app.paramedicos.web.dto.MedicalRecordDto;
import app.paramedicos.web.dto.PatientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class MedicalRecordController {

    public final MedicalRecordService medicalRecordService;

    @GetMapping
    public ResponseEntity<List<MedicalRecordDto>> showAll(){

        return ResponseEntity.ok(medicalRecordService.showAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecord> findOne(@PathVariable Long id){
        return ResponseEntity.ok(medicalRecordService.findOne(id));
    }

    @PostMapping
    public ResponseEntity<MedicalRecord> create(@RequestBody MedicalRecord medicalRecord
            , UriComponentsBuilder ucb){
        MedicalRecord newRecord = medicalRecordService.create(medicalRecord);

        URI recordLocation = ucb
                .path("/api/records/{id}")
                .buildAndExpand(newRecord.getId())
                .toUri();

        return ResponseEntity.created(recordLocation).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecord> update(@PathVariable Long id, @RequestBody MedicalRecord record){

        return ResponseEntity.ok(medicalRecordService.update(record, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        medicalRecordService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
