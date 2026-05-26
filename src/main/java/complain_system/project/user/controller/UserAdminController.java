package complain_system.project.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import complain_system.project.auth.service.AuthService;
import complain_system.project.user.dto.UserResponse;

@Validated
@RestController
@RequestMapping("/api/users")
public class UserAdminController {

    private final AuthService authService;

    public UserAdminController(AuthService authService) {
        this.authService = authService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @GetMapping("/admin-directors")
    public ResponseEntity<List<UserResponse>> getAdminDirectors() {
        return ResponseEntity.ok(authService.getAdminDirectors());
    }
}
