package io.allteran.plutos.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "salary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Salary {
    @Id
    private String id;
    private LocalDateTime shiftStart;
    private LocalDateTime shiftEnd;
    private String reportMonth;
    private String reportYear;
    private int workedHours;
    private double ratePerHour;
    private Company company;
}
