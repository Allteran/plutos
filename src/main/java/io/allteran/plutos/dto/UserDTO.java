package io.allteran.plutos.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
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
    @JsonView(Views.Public.class)
    private String id;
    @JsonView(Views.Public.class)
    private String email;
    @JsonView(Views.Public.class)
    private String firstName;
    @JsonView(Views.Public.class)
    private String lastName;
    @JsonView(Views.Internal.class)
    private String password;
    @JsonView(Views.Internal.class)
    private String passwordConfirm;
    @JsonView(Views.Internal.class)
    private String newPassword;
    @JsonView(Views.Public.class)
    private String countryId;
    @JsonView(Views.Public.class)
    private LocalDate dateOfBirth;
    @JsonView(Views.Public.class)
    private Set<Role> roles;
    @JsonView(Views.Public.class)
    private Set<String> privilegeIds;
    @JsonView(Views.Public.class)
    private boolean active;
    @JsonView(Views.Public.class)
    private double ratePerHour;
    @JsonView(Views.Public.class)
    private String employerId;
}
