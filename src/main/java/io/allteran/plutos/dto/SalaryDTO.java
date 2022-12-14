package io.allteran.plutos.dto;

import io.allteran.plutos.domain.Company;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDTO {
    private String id;
    private String userId;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private double workedHours;
    private double ratePerHour;
    private String companyId;

    public SalaryDTO(String userId, LocalDateTime shiftStart, LocalDateTime shiftEnd, double workedHours, double ratePerHour, String companyId) {
        this.userId = userId;
        this.shiftStart = shiftStart;
        this.shiftEnd = shiftEnd;
        this.workedHours = workedHours;
        this.ratePerHour = ratePerHour;
        this.companyId = companyId;
    }
}
