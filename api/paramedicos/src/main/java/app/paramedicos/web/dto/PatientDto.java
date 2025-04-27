package app.paramedicos.web.dto;

import app.paramedicos.domain.model.Patient;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientDto {

    Long id;

    public static PatientDto fromEntity(Patient patient){
        return PatientDto.builder()
                .id(patient.getId())
                .build();
    }
}
