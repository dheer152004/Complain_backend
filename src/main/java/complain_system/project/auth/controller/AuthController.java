package complain_system.project.auth.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import complain_system.project.auth.dto.AuthResponse;
import complain_system.project.auth.dto.RegisterRequest;
import complain_system.project.auth.dto.LoginRequest;
import complain_system.project.auth.dto.LogoutResponse;
import complain_system.project.role.dto.RoleResponse;
import complain_system.project.auth.service.AuthService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout() {
        return ResponseEntity.ok(authService.logout());
    }

    @GetMapping("/roles")
    public ResponseEntity<Set<RoleResponse>> roles() {
        return ResponseEntity.ok(authService.getRoles());
    }
}
