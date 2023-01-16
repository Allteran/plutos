package io.allteran.plutos.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "usr")
public class User implements UserDetails {
    @Id
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    @Transient
    private String passwordConfirm;
    @Transient
    private String newPassword;
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }
}
