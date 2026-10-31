package com.ahujanursinghome.hospital_management.AuthenticationServiceTest;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ahujanursinghome.hospital_management.auth.AuthenticationService;
import com.ahujanursinghome.hospital_management.auth.UpdatePasswordDTO;
import com.ahujanursinghome.hospital_management.config.JwtService;
import com.ahujanursinghome.hospital_management.user.Role;
import com.ahujanursinghome.hospital_management.user.User;
import com.ahujanursinghome.hospital_management.user.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    void updatePassword_UserExists_ReturnsSuccessMessage() {
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO();
        updatePasswordDTO.setEmail("fmohd743@gmail.com");
        updatePasswordDTO.setPassword("12345");
        User user = new User();
        user.builder().firstname("faisal").lastname("faisal").email("fmohd743@gmail.com")
                .password("mockEncodedPassword").role(Role.USER)
                .build();

        when(userRepository.findByEmail("fmohd743@gmail.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("12345"))
                .thenReturn("mockEncodedPassword");

        String result = authenticationService.updatePassword(updatePasswordDTO);
        assertEquals("Password updated successfully!", result);

        assertEquals("mockEncodedPassword", user.getPassword());

        verify(userRepository).save(user);

    }

}
