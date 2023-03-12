package io.allteran.plutos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftDTO {
    private String id;
    private String userId;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    int breakDuration;
    private double workedHours;
    private double ratePerHour;
    private double income;
    private String companyId;
}
