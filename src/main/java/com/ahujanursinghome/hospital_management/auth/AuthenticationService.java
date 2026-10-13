package com.ahujanursinghome.hospital_management.auth;

import com.ahujanursinghome.hospital_management.config.JwtService;
import com.ahujanursinghome.hospital_management.user.Role;
import com.ahujanursinghome.hospital_management.user.User;
import com.ahujanursinghome.hospital_management.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse register(RegisterRequest request) {
    var user= User.builder()
            .firstname(request.getFirstname())
            .lastname(request.getLastname())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();
    userRepository.save(user);
    var jwtToken=jwtService.generateToken(user);
    return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();



    }

    public AuthenticationResponse authenticate(Authenticationrequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user=userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        var jwtToken=jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public String updatePassword(UpdatePasswordDTO updatePasswordDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(updatePasswordDTO.getEmail());

        if (optionalUser.isEmpty()) {
            return "User not found with given email";
        }

        User user = optionalUser.get();
        String newPassword = passwordEncoder.encode(updatePasswordDTO.getPassword());
        user.setPassword(newPassword);
        userRepository.save(user);

        return "Password updated successfully!";
    }
}

