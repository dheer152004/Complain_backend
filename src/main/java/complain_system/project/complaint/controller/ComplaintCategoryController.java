package complain_system.project.complaint.controller;

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

import complain_system.project.dto.ComplaintCategoryRequest;
import complain_system.project.dto.ComplaintCategoryResponse;
import complain_system.project.complaint.service.ComplaintCategoryService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/complaint-categories")
public class ComplaintCategoryController {

    private final ComplaintCategoryService complaintCategoryService;

    public ComplaintCategoryController(ComplaintCategoryService complaintCategoryService) {
        this.complaintCategoryService = complaintCategoryService;
    }

    @GetMapping
    public ResponseEntity<Set<ComplaintCategoryResponse>> getCategories() {
        return ResponseEntity.ok(complaintCategoryService.getCategories());
    }

    @PreAuthorize("hasRole('ADMIN_DIRECTOR')")
    @PostMapping
    public ResponseEntity<ComplaintCategoryResponse> createCategory(@Valid @RequestBody ComplaintCategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(complaintCategoryService.createCategory(request));
    }
}
