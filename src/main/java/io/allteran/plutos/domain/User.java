package io.allteran.plutos.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
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
    private LocalDate dateOfBirth;
    private Set<Role> roles;
    private Set<String> privilegeIds;
    private boolean active;
    private String employerId;
    private double ratePerHour;


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
