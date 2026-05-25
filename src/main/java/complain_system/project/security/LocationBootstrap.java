package complain_system.project.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import complain_system.project.model.Location;
import complain_system.project.repository.LocationRepository;

@Component
public class LocationBootstrap implements CommandLineRunner {

    private final LocationRepository locationRepository;

    public LocationBootstrap(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public void run(String... args) {
        seedLocation("A BLOCK");
        seedLocation("B BLOCK");
        seedLocation("C BLOCK");
        seedLocation("D BLOCK");
        seedLocation("E BLOCK");
        seedLocation("H1");
        seedLocation("H2");
        seedLocation("H3");
        seedLocation("H4");
        seedLocation("H5");
        seedLocation("MESS");
    }

    private void seedLocation(String name) {
        String normalizedName = Location.normalize(name);
        String normalizedFloor = "";
        String normalizedRoomNo = "";

        if (!locationRepository.existsByNameAndFloorAndRoomNo(normalizedName, normalizedFloor, normalizedRoomNo)) {
            locationRepository.save(new Location(normalizedName, normalizedFloor, normalizedRoomNo));
        }
    }
}