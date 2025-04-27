package app.paramedicos.application.usecase;


import app.paramedicos.domain.model.MedicalRecord;
import app.paramedicos.domain.model.Patient;
import app.paramedicos.web.dto.MedicalRecordDto;
import app.paramedicos.web.dto.PatientDto;

import java.util.List;

public interface MedicalRecordService {
    public MedicalRecord create(MedicalRecord medicalRecord);

    public List<MedicalRecordDto> showAll();

    public MedicalRecord findOne(Long id);
    public MedicalRecord findOneByPatient(Long id);

    public MedicalRecord update(MedicalRecord medicalRecord, long id);

    public void delete(long id);
}
