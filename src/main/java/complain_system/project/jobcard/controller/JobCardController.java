package complain_system.project.jobcard.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import complain_system.project.dto.JobCardRequest;
import complain_system.project.dto.JobCardResponse;
import complain_system.project.jobcard.model.JobStatus;
import complain_system.project.jobcard.service.JobCardService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/job-cards")
public class JobCardController {

    private final JobCardService jobCardService;

    public JobCardController(JobCardService jobCardService) {
        this.jobCardService = jobCardService;
    }

    @GetMapping
    public ResponseEntity<Set<JobCardResponse>> getAll() {
        return ResponseEntity.ok(jobCardService.getJobCards());
    }

    @PreAuthorize("hasRole('MAINTENANCE_CELL_ADMIN')")
    @PostMapping
    public ResponseEntity<JobCardResponse> create(@Valid @RequestBody JobCardRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(jobCardService.createJobCard(request));
    }

    @PreAuthorize("hasRole('MAINTENANCE_CELL_ADMIN')")
    @PutMapping("/{id}/status")
    public ResponseEntity<JobCardResponse> updateStatus(@PathVariable("id") Long id,
            @RequestBody UpdateStatusRequest statusReq) {
        JobStatus status = JobStatus.valueOf(statusReq.getStatus().toUpperCase());
        return ResponseEntity.ok(jobCardService.updateStatus(id, status, statusReq.getRemark()));
    }

    @PostMapping("/{id}/satisfy")
    public ResponseEntity<JobCardResponse> setSatisfaction(@PathVariable("id") Long id) {
        return ResponseEntity.ok(jobCardService.setSatisfaction(id, true));
    }

    static class UpdateStatusRequest {
        private String status;
        private String remark;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
