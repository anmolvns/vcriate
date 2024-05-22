package com.restaurant.vcriate.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.restaurant.vcriate.models.Role;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRegister {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
}
