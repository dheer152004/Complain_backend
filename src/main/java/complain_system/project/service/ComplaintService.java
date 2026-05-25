package complain_system.project.service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import complain_system.project.dto.ComplaintRequest;
import complain_system.project.dto.ComplaintResponse;
import complain_system.project.model.Complaint;
import complain_system.project.model.ComplaintCategory;
import complain_system.project.model.Location;
import complain_system.project.model.Role;
import complain_system.project.model.User;
import complain_system.project.repository.ComplaintCategoryRepository;
import complain_system.project.repository.ComplaintRepository;
import complain_system.project.repository.LocationRepository;
import complain_system.project.repository.UserRepository;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final ComplaintCategoryRepository complaintCategoryRepository;

    public ComplaintService(ComplaintRepository complaintRepository, UserRepository userRepository,
            LocationRepository locationRepository, ComplaintCategoryRepository complaintCategoryRepository) {
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
        this.complaintCategoryRepository = complaintCategoryRepository;
    }

    public ComplaintResponse createComplaint(ComplaintRequest request, Authentication authentication) {
        User complainBy = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Location location = locationRepository.findById(request.getLocationId())
                .orElseThrow(() -> new IllegalArgumentException("Location not found"));
        ComplaintCategory category = complaintCategoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Complaint category not found"));

        Complaint complaint = new Complaint(complainBy, location, request.getDescription(), category,
                request.getPriority());
        complaint.setResolved(false);

        return toResponse(complaintRepository.save(complaint));
    }

    public Set<ComplaintResponse> getComplaints() {
        return complaintRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private ComplaintResponse toResponse(Complaint complaint) {
        ComplaintResponse response = new ComplaintResponse();
        response.setComplainId(complaint.getComplainId());
        response.setComplainByUserId(complaint.getComplainBy() == null ? null : complaint.getComplainBy().getId());
        response.setComplainByName(complaint.getComplainBy() == null ? null : complaint.getComplainBy().getName());
        response.setComplainByRoles(complaint.getComplainBy() == null ? Set.of() : complaint.getComplainBy().getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toCollection(LinkedHashSet::new)));
        response.setLocationId(complaint.getLocation() == null ? null : complaint.getLocation().getLocationId());
        response.setLocationName(complaint.getLocation() == null ? null : complaint.getLocation().getName());
        response.setLocationFloor(complaint.getLocation() == null ? null : complaint.getLocation().getFloor());
        response.setLocationRoomNo(complaint.getLocation() == null ? null : complaint.getLocation().getRoomNo());
        response.setCreatedTime(complaint.getCreatedTime());
        response.setResolvedTime(complaint.getResolvedTime());
        response.setDescription(complaint.getDescription());
        response.setCategoryId(complaint.getCategory() == null ? null : complaint.getCategory().getCategoryId());
        response.setCategoryName(complaint.getCategory() == null ? null : complaint.getCategory().getName());
        response.setPriority(complaint.getPriority());
        response.setResolved(complaint.isResolved());
        return response;
    }
}