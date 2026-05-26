package complain_system.project.location.service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import complain_system.project.branch.model.UniBranch;
import complain_system.project.branch.repository.UniBranchRepository;
import complain_system.project.location.dto.LocationRequest;
import complain_system.project.location.dto.LocationResponse;
import complain_system.project.location.model.Location;
import complain_system.project.location.repository.LocationRepository;
import complain_system.project.role.model.Role;
import complain_system.project.user.model.User;
import complain_system.project.user.repository.UserRepository;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final UniBranchRepository uniBranchRepository;
    private final UserRepository userRepository;

    public LocationService(LocationRepository locationRepository, UniBranchRepository uniBranchRepository,
            UserRepository userRepository) {
        this.locationRepository = locationRepository;
        this.uniBranchRepository = uniBranchRepository;
        this.userRepository = userRepository;
    }

    public LocationResponse createLocation(Long branchId, LocationRequest request) {
        UniBranch branch = resolveWritableBranch(branchId);
        if (request.getBranchId() != null && !request.getBranchId().equals(branchId)) {
            throw new IllegalArgumentException("Request branchId must match path branchId");
        }
        String name = Location.normalize(request.getName());
        String floor = Location.normalize(request.getFloor());
        String roomNo = Location.normalize(request.getRoomNo());
        if (locationRepository.existsByBranch_BranchIdAndName(branch.getBranchId(), name)) {
            throw new IllegalArgumentException("Location already exists");
        }

        Location loc = new Location();
        loc.setBranch(branch);
        loc.setName(name);
        loc.setFloor(floor);
        loc.setRoomNo(roomNo);
        Location saved = locationRepository.save(loc);
        return toResponse(saved);
    }

    public Set<LocationResponse> getLocations(Long branchId) {
        resolveBranch(branchId);
        return locationRepository.findAllByBranch_BranchId(branchId).stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public LocationResponse getLocation(Long branchId, Long locationId) {
        resolveBranch(branchId);
        Location location = locationRepository.findByBranch_BranchIdAndLocationId(branchId, locationId)
                .orElseThrow(() -> new IllegalArgumentException("Location not found for this branch"));
        return toResponse(location);
    }

    public LocationResponse getLocationByName(Long branchId, String locationName) {
        resolveBranch(branchId);
        String normalizedName = Location.normalize(locationName);
        Location location = locationRepository.findByBranch_BranchIdAndName(branchId, normalizedName)
                .orElseThrow(() -> new IllegalArgumentException("Location not found for this branch"));
        return toResponse(location);
    }

        private UniBranch resolveBranch(Long branchId) {
        return uniBranchRepository.findById(branchId)
            .orElseThrow(() -> new IllegalArgumentException("Branch not found"));
        }

        private UniBranch resolveWritableBranch(Long branchId) {
        UniBranch requestedBranch = uniBranchRepository.findById(branchId)
                .orElseThrow(() -> new IllegalArgumentException("Branch not found"));

        User currentUser = currentUser();
        boolean superAdmin = currentUser.getRoles() != null
                && currentUser.getRoles().stream().anyMatch(role -> "SUPER_ADMIN".equals(role.getName()));
        if (superAdmin) {
            return requestedBranch;
        }

        Long userBranchId = currentUser.getBranch() == null ? null : currentUser.getBranch().getBranchId();
        if (userBranchId == null || !userBranchId.equals(branchId)) {
            throw new AccessDeniedException("You can only access locations from your own branch");
        }
        return requestedBranch;
    }

    private User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new AccessDeniedException("Authentication required");
        }

        return userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AccessDeniedException("Authenticated user not found"));
    }

    private LocationResponse toResponse(Location location) {
        Long branchId = location.getBranch() == null ? null : location.getBranch().getBranchId();
        return new LocationResponse(location.getLocationId(), branchId, location.getName(), location.getFloor(),
                location.getRoomNo());
    }
}
