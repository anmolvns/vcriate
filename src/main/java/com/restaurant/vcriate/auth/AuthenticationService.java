package com.restaurant.vcriate.auth;

import com.restaurant.vcriate.config.JwtService;
import com.restaurant.vcriate.exceptions.EtAuthException;
import com.restaurant.vcriate.exceptions.UserAlreadyExistsException;
import com.restaurant.vcriate.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import com.restaurant.vcriate.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.restaurant.vcriate.repositories.UserRepository;

import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    @Autowired
    UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(AuthenticationRegister request) {

        // Check if user already exists
        if (repository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with email: " + request.getEmail());
        }

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 15);

        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            var user = repository.findByEmail(request.getEmail());

            if (user == null) {
                throw new UserNotFoundException("User not found with email: " + request.getEmail());
            }

            // Check if provided password matches the stored password
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new EtAuthException("Invalid email or password");
            }

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (UserNotFoundException ex) {
            throw ex;
        } catch (EtAuthException ex) {
            throw ex;
        } catch (Exception e){
            throw e;
        }
    }
}
