package complain_system.project.dto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import complain_system.project.jobcard.model.JobStatus;

public class JobCardResponse {

    private Long jobId;
    private Long complaintId;
    private Long complainByUserId;
    private String assignedToName;
    private Long locationId;
    private String locationName;
    private LocalDateTime date;
    private String description;
    private JobStatus status;
    private Boolean satisfyByUser;
    private String remark;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(Long complaintId) {
        this.complaintId = complaintId;
    }

    public Long getComplainByUserId() {
        return complainByUserId;
    }

    public void setComplainByUserId(Long complainByUserId) {
        this.complainByUserId = complainByUserId;
    }

    public String getAssignedToName() {
        return assignedToName;
    }

    public void setAssignedToName(String assignedToName) {
        this.assignedToName = assignedToName;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    public Boolean getSatisfyByUser() {
        return satisfyByUser;
    }

    public void setSatisfyByUser(Boolean satisfyByUser) {
        this.satisfyByUser = satisfyByUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
