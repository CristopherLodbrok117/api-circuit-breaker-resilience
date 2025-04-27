package app.paramedicos.application.usecase;

import app.paramedicos.domain.model.MedicalRecord;
import app.paramedicos.domain.model.Patient;
import app.paramedicos.web.dto.PatientDto;

import java.util.List;

public interface PatientService {

    public Patient create();

    public List<PatientDto> showAllCompleted();
    public List<PatientDto> showAllPending();

    public Patient findOne(Long id);
    public Patient findOneByFolio(String folio);

    public Patient update(Patient patient, long id);

    public void delete(long id);
}
