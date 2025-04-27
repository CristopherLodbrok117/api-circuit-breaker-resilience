package app.paramedicos.web.dto;

import app.paramedicos.domain.model.MedicalRecord;
import app.paramedicos.domain.model.Patient;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MedicalRecordDto {
    private Long id;

    private LocalDateTime date;

    public static MedicalRecordDto fromEntity(MedicalRecord medicalRecord){
        return MedicalRecordDto.builder()
                .id(medicalRecord.getId())
                .date(medicalRecord.getDate())
                .build();
    }
}
