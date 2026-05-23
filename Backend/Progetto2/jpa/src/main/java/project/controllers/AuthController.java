package project.controllers;

import project.config.auth.*;
import project.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register( @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody AuthenticationRequest request) {

        return ResponseEntity.ok(
                authService.authenticate(request)
        );
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(
            @RequestBody ForgotPasswordRequest request
    ) {
        authService.forgotPassword(request);
        return ResponseEntity.ok().build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(
            @RequestBody ResetPasswordRequest request
    ) {
        authService.resetPassword(request);
        return ResponseEntity.ok().build();
    }
}