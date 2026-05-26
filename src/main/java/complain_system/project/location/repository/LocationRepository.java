package complain_system.project.location.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import complain_system.project.location.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByBranch_BranchIdAndLocationId(Long branchId, Long locationId);

    Optional<Location> findByBranch_BranchIdAndName(Long branchId, String name);

    boolean existsByBranch_BranchIdAndName(Long branchId, String name);

    java.util.List<Location> findAllByBranch_BranchId(Long branchId);
}
