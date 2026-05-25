package complain_system.project.service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import complain_system.project.dto.LocationRequest;
import complain_system.project.dto.LocationResponse;
import complain_system.project.model.Location;
import complain_system.project.repository.LocationRepository;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public LocationResponse createLocation(LocationRequest request) {
        String name = Location.normalize(request.getName());
        String floor = Location.normalize(request.getFloor());
        String roomNo = Location.normalize(request.getRoomNo());

        if (locationRepository.existsByNameAndFloorAndRoomNo(name, floor, roomNo)) {
            throw new IllegalArgumentException("Location already exists");
        }

        Location location = new Location(name, floor, roomNo);
        Location savedLocation = locationRepository.save(location);
        return toResponse(savedLocation);
    }

    public Set<LocationResponse> getLocations() {
        return locationRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public boolean existsByNameAndFloorAndRoomNo(String name, String floor, String roomNo) {
        return locationRepository.existsByNameAndFloorAndRoomNo(Location.normalize(name), Location.normalize(floor),
                Location.normalize(roomNo));
    }

    private LocationResponse toResponse(Location location) {
        return new LocationResponse(location.getLocationId(), location.getName(), location.getFloor(), location.getRoomNo());
    }
}