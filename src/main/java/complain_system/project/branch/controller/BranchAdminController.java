package complain_system.project.branch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import complain_system.project.branch.dto.AdminDirectorRequest;
import complain_system.project.branch.dto.AdminDirectorResponse;
import complain_system.project.auth.service.AuthService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/branches")
public class BranchAdminController {

    private final AuthService authService;

    public BranchAdminController(AuthService authService) {
        this.authService = authService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping("/{branchId}/admin-director")
    public ResponseEntity<AdminDirectorResponse> createAdminDirector(@PathVariable Long branchId,
            @Valid @RequestBody AdminDirectorRequest request) {
        AdminDirectorResponse resp = authService.createAdminDirector(branchId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }
}
