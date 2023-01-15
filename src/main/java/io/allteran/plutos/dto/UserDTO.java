package io.allteran.plutos.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.allteran.plutos.domain.Privilege;
import io.allteran.plutos.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
//    @JsonIgnore
    private String password;
//    @JsonIgnore
    private String passwordConfirm;
    private String countryId;
    //saves only IDs of some salary, so it won't be inner object in Mongo
    private List<String> salaryList;
    private float totalSalary;
    private float totalTax;
    private float netPay;
    private LocalDate dateOfBirth;
    private Set<Role> roles;
    private Set<String> privilegeIds;
    private boolean active;

}
