package complain_system.project.location.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import complain_system.project.location.dto.LocationResponse;
import complain_system.project.location.service.LocationService;

@Validated
@RestController
@RequestMapping("/api/locations")
public class LegacyLocationController {

    private final LocationService locationService;

    public LegacyLocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @GetMapping
    public ResponseEntity<Set<LocationResponse>> getLocations(@RequestParam Long branchId) {
        return ResponseEntity.ok(locationService.getLocations(branchId));
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<LocationResponse> getLocation(@RequestParam Long branchId, @PathVariable Long locationId) {
        return ResponseEntity.ok(locationService.getLocation(branchId, locationId));
    }

    @GetMapping("/by-name")
    public ResponseEntity<LocationResponse> getLocationByName(@RequestParam Long branchId,
            @RequestParam String name) {
        return ResponseEntity.ok(locationService.getLocationByName(branchId, name));
    }
}
