package com.ahujanursinghome.hospital_management.auth;

import com.ahujanursinghome.hospital_management.user.User;
import com.ahujanursinghome.hospital_management.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor

public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
 private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse>register(
            @RequestBody RegisterRequest request
    ){
return ResponseEntity.ok(authenticationService.register(request));
    }
    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponse>authenticate(
            @RequestBody Authenticationrequest request
    ){
        String password=passwordEncoder.encode(request.getPassword());
        System.out.println("Encoded password: " + password);

        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
    @PatchMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {

        Optional<User> optionalUser = userRepository.findByEmail(updatePasswordDTO.getEmail());

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found with given email");
        }else {
            if (updatePasswordDTO.getEmail() == null || updatePasswordDTO.getPassword() == null) {
                return ResponseEntity.badRequest().body("Email and password must not be null");
            }

            String result = authenticationService.updatePassword(updatePasswordDTO);
            return ResponseEntity.ok(result);
        }
    }
}
