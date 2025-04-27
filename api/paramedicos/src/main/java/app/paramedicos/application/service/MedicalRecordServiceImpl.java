package app.paramedicos.application.service;

import app.paramedicos.application.usecase.MedicalRecordService;
import app.paramedicos.domain.exception.MedicalRecordException;
import app.paramedicos.domain.model.MedicalRecord;
import app.paramedicos.domain.repository.MedicalRecordRepository;
import app.paramedicos.web.dto.MedicalRecordDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;

    @Override
    public MedicalRecord create(MedicalRecord medicalRecord) {

        return medicalRecordRepository.save(medicalRecord);
    }

    @Override
    public List<MedicalRecordDto> showAll() {
        return medicalRecordRepository.findAll().stream()
                .map(MedicalRecordDto::fromEntity)
                .toList();
    }

    @Override
    public MedicalRecord findOne(Long id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new MedicalRecordException("No se encontro el record con ID: " + id));
    }

    @Override
    public MedicalRecord findOneByPatient(Long id) {
        return medicalRecordRepository.findByPatient_Id(id)
                .orElseThrow(() -> new MedicalRecordException("No se encontro el record del paciente con ID: " + id));
    }

    @Override
    public MedicalRecord update(MedicalRecord medicalRecord, long id) {
        MedicalRecord dbMedicalRecord = findOne(id);

        medicalRecord.setId(dbMedicalRecord.getId());

        return medicalRecordRepository.save(medicalRecord);
    }

    @Override
    public void delete(long id) {
        if(!medicalRecordRepository.existsById(id)){
            throw new MedicalRecordException("No se encontro el record con ID: " + id);
        }

        medicalRecordRepository.deleteById(id);

    }
}
