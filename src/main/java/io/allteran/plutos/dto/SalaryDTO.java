package io.allteran.plutos.dto;

import io.allteran.plutos.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDTO {
    private String id;
    private String userId;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private String reportMonth;
    private String reportYear;
    private double workedHours;
    private double ratePerHour;
    private String companyId;
}
