package io.allteran.plutos.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usr")
public class User {
    @Id
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String passwordConfirm;
    private String occupation;
    private Country residence;
    private List<Salary> salaryList;
    private float totalSalary;
    private float totalTax;
    private float netPay;
    private List<Tax> taxList;
    private LocalDate dateOfBirth;
    private Set<Role> roles;
    private Set<Privilege> privileges;
}
