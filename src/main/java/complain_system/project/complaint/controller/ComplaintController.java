package complain_system.project.complaint.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import complain_system.project.dto.ComplaintRequest;
import complain_system.project.dto.ComplaintResponse;
import complain_system.project.complaint.service.ComplaintService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping
    public ResponseEntity<ComplaintResponse> createComplaint(@Valid @RequestBody ComplaintRequest request,
            Authentication authentication) {
        return ResponseEntity.status(HttpStatus.CREATED).body(complaintService.createComplaint(request, authentication));
    }

    @GetMapping
    public ResponseEntity<Set<ComplaintResponse>> getComplaints() {
        return ResponseEntity.ok(complaintService.getComplaints());
    }
}
