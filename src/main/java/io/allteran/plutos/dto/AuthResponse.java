package io.allteran.plutos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String userId;
    private String login;
    private String token;
    private String message;

    public AuthResponse(String login, String token, String message) {
        this.login = login;
        this.token = token;
        this.message = message;
    }
}
