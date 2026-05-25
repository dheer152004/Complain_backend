package complain_system.project.location.service;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import complain_system.project.location.dto.LocationRequest;
import complain_system.project.location.dto.LocationResponse;
import complain_system.project.location.model.Location;
import complain_system.project.location.repository.LocationRepository;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public LocationResponse createLocation(LocationRequest request) {
        String name = Location.normalize(request.getName());
        String floor = request.getFloor();
        String roomNo = request.getRoomNo();
        if (locationRepository.existsByNameAndFloorAndRoomNo(name, floor, roomNo)) {
            throw new IllegalArgumentException("Location already exists");
        }

        Location loc = new Location();
        loc.setName(name);
        loc.setFloor(floor);
        loc.setRoomNo(roomNo);
        Location saved = locationRepository.save(loc);
        return new LocationResponse(saved.getLocationId(), saved.getName(), saved.getFloor(), saved.getRoomNo());
    }

    public Set<LocationResponse> getLocations() {
        return locationRepository.findAll().stream()
                .map(l -> new LocationResponse(l.getLocationId(), l.getName(), l.getFloor(), l.getRoomNo()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
