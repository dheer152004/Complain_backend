package complain_system.project.branch.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import complain_system.project.branch.dto.UniBranchRequest;
import complain_system.project.branch.dto.UniBranchResponse;
import complain_system.project.branch.service.BranchService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/branches")
public class BranchController {

    private final BranchService branchService;

    public BranchController(BranchService branchService) {
        this.branchService = branchService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @PostMapping
    public ResponseEntity<UniBranchResponse> createBranch(@Valid @RequestBody UniBranchRequest req) {
        UniBranchResponse resp = branchService.createBranch(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);
    }

    @GetMapping
    public ResponseEntity<List<UniBranchResponse>> listBranches() {
        return ResponseEntity.ok(branchService.listBranches());
    }
}
