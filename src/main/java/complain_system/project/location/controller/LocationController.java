package complain_system.project.location.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import complain_system.project.location.dto.LocationRequest;
import complain_system.project.location.dto.LocationResponse;
import complain_system.project.location.service.LocationService;
import jakarta.validation.Valid;

@Validated
@RestController
@RequestMapping("/api/branches/{branchId}/locations")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<Set<LocationResponse>> getLocations(@PathVariable Long branchId) {
        return ResponseEntity.ok(locationService.getLocations(branchId));
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<LocationResponse> getLocation(@PathVariable Long branchId, @PathVariable Long locationId) {
        return ResponseEntity.ok(locationService.getLocation(branchId, locationId));
    }

    @GetMapping("/by-name")
    public ResponseEntity<LocationResponse> getLocationByName(@PathVariable Long branchId,
            @RequestParam String name) {
        return ResponseEntity.ok(locationService.getLocationByName(branchId, name));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN_DIRECTOR')")
    @PostMapping
    public ResponseEntity<LocationResponse> createLocation(@PathVariable Long branchId,
            @Valid @RequestBody LocationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.createLocation(branchId, request));
    }
}
