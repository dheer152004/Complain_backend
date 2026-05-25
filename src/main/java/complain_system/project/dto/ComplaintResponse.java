package complain_system.project.dto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import complain_system.project.complaint.model.ComplaintPriority;

public class ComplaintResponse {

    private Long complainId;
    private Long complainByUserId;
    private String complainByName;
    private Set<String> complainByRoles = new LinkedHashSet<>();
    private Long locationId;
    private String locationName;
    private String locationFloor;
    private String locationRoomNo;
    private LocalDateTime createdTime;
    private LocalDateTime resolvedTime;
    private String description;
    private Long categoryId;
    private String categoryName;
    private ComplaintPriority priority;
    private boolean resolved;

    public Long getComplainId() {
        return complainId;
    }

    public void setComplainId(Long complainId) {
        this.complainId = complainId;
    }

    public Long getComplainByUserId() {
        return complainByUserId;
    }

    public void setComplainByUserId(Long complainByUserId) {
        this.complainByUserId = complainByUserId;
    }

    public String getComplainByName() {
        return complainByName;
    }

    public void setComplainByName(String complainByName) {
        this.complainByName = complainByName;
    }

    public Set<String> getComplainByRoles() {
        return complainByRoles;
    }

    public void setComplainByRoles(Set<String> complainByRoles) {
        this.complainByRoles = complainByRoles == null ? new LinkedHashSet<>() : new LinkedHashSet<>(complainByRoles);
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

    public String getLocationFloor() {
        return locationFloor;
    }

    public void setLocationFloor(String locationFloor) {
        this.locationFloor = locationFloor;
    }

    public String getLocationRoomNo() {
        return locationRoomNo;
    }

    public void setLocationRoomNo(String locationRoomNo) {
        this.locationRoomNo = locationRoomNo;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getResolvedTime() {
        return resolvedTime;
    }

    public void setResolvedTime(LocalDateTime resolvedTime) {
        this.resolvedTime = resolvedTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ComplaintPriority getPriority() {
        return priority;
    }

    public void setPriority(ComplaintPriority priority) {
        this.priority = priority;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
}