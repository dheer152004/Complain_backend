package complain_system.project.location;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import complain_system.project.branch.model.UniBranch;
import complain_system.project.branch.repository.UniBranchRepository;
import complain_system.project.location.model.Location;
import complain_system.project.location.repository.LocationRepository;

@Component
public class LocationBootstrap implements CommandLineRunner {

    private final LocationRepository locationRepository;
    private final UniBranchRepository uniBranchRepository;

    public LocationBootstrap(LocationRepository locationRepository, UniBranchRepository uniBranchRepository) {
        this.locationRepository = locationRepository;
        this.uniBranchRepository = uniBranchRepository;
    }

    @Override
    public void run(String... args) {
        List<UniBranch> branches = uniBranchRepository.findAll();
        for (UniBranch branch : branches) {
            seedLocation(branch, "A BLOCK");
            seedLocation(branch, "B BLOCK");
            seedLocation(branch, "C BLOCK");
            seedLocation(branch, "D BLOCK");
            seedLocation(branch, "E BLOCK");
            seedLocation(branch, "H1");
            seedLocation(branch, "H2");
            seedLocation(branch, "H3");
            seedLocation(branch, "H4");
            seedLocation(branch, "H5");
            seedLocation(branch, "MESS");
        }
    }

    private void seedLocation(UniBranch branch, String name) {
        String normalizedName = Location.normalize(name);
        String normalizedFloor = "";
        String normalizedRoomNo = "";

        if (!locationRepository.existsByBranch_BranchIdAndName(branch.getBranchId(), normalizedName)) {
            locationRepository.save(new Location(branch, normalizedName, normalizedFloor, normalizedRoomNo));
        }
    }
}