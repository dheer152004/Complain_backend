package complain_system.project.role.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import complain_system.project.dto.RoleRequest;
import complain_system.project.dto.RoleResponse;
import complain_system.project.auth.service.AuthService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final AuthService authService;

    public RoleController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<Set<RoleResponse>> getRoles() {
        return ResponseEntity.ok(authService.getRoles());
    }

    @PreAuthorize("hasRole('ADMIN_DIRECTOR')")
    @PostMapping
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.createRole(request));
    }
}
