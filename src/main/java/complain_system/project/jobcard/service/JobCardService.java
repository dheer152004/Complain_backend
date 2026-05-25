package complain_system.project.jobcard.service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import complain_system.project.jobcard.dto.JobCardRequest;
import complain_system.project.jobcard.dto.JobCardResponse;
import complain_system.project.complaint.model.Complaint;
import complain_system.project.jobcard.model.JobCard;
import complain_system.project.jobcard.model.JobStatus;
import complain_system.project.location.model.Location;
import complain_system.project.complaint.repository.ComplaintRepository;
import complain_system.project.jobcard.repository.JobCardRepository;
import complain_system.project.location.repository.LocationRepository;

@Service
public class JobCardService {

    private final JobCardRepository jobCardRepository;
    private final ComplaintRepository complaintRepository;
    private final LocationRepository locationRepository;

    public JobCardService(JobCardRepository jobCardRepository, ComplaintRepository complaintRepository,
            LocationRepository locationRepository) {
        this.jobCardRepository = jobCardRepository;
        this.complaintRepository = complaintRepository;
        this.locationRepository = locationRepository;
    }

    public JobCardResponse createJobCard(JobCardRequest request) {
        Complaint complaint = complaintRepository.findById(request.getComplaintId())
                .orElseThrow(() -> new IllegalArgumentException("Complaint not found"));

        Location location = null;
        if (request.getLocationId() != null) {
            location = locationRepository.findById(request.getLocationId())
                    .orElseThrow(() -> new IllegalArgumentException("Location not found"));
        } else {
            location = complaint.getLocation();
        }

        String description = complaint.getDescription();
        if (request.getDescription() != null && !request.getDescription().isBlank()) {
            description = description + "\n\nMaintenance note: " + request.getDescription();
        }

        JobCard jobCard = new JobCard(complaint, request.getAssignedToName(), location, description);
        JobCard saved = jobCardRepository.save(jobCard);
        return toResponse(saved);
    }

    public Set<JobCardResponse> getJobCards() {
        return jobCardRepository.findAll().stream().map(this::toResponse)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public JobCardResponse updateStatus(Long jobId, JobStatus status, String remark) {
        JobCard jobCard = jobCardRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job card not found"));
        jobCard.setStatus(status);
        if (remark != null) {
            jobCard.setRemark(remark);
        }
        return toResponse(jobCardRepository.save(jobCard));
    }

    public JobCardResponse setSatisfaction(Long jobId, boolean satisfy) {
        JobCard jobCard = jobCardRepository.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job card not found"));
        jobCard.setSatisfyByUser(satisfy);
        return toResponse(jobCardRepository.save(jobCard));
    }

    private JobCardResponse toResponse(JobCard jc) {
        JobCardResponse r = new JobCardResponse();
        r.setJobId(jc.getJobId());
        r.setComplaintId(jc.getComplaint() == null ? null : jc.getComplaint().getComplainId());
        r.setComplainByUserId(jc.getComplaint() == null || jc.getComplaint().getComplainBy() == null ? null
                : jc.getComplaint().getComplainBy().getId());
        r.setAssignedToName(jc.getAssignedToName());
        r.setLocationId(jc.getLocation() == null ? null : jc.getLocation().getLocationId());
        r.setLocationName(jc.getLocation() == null ? null : jc.getLocation().getName());
        r.setDate(jc.getDate());
        r.setDescription(jc.getDescription());
        r.setStatus(jc.getStatus());
        r.setSatisfyByUser(jc.getSatisfyByUser());
        r.setRemark(jc.getRemark());
        return r;
    }
}
