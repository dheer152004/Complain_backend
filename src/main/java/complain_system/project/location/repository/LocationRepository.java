package complain_system.project.location.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import complain_system.project.location.model.Location;

public interface LocationRepository extends JpaRepository<Location, Long> {

    Optional<Location> findByNameAndFloorAndRoomNo(String name, String floor, String roomNo);

    boolean existsByNameAndFloorAndRoomNo(String name, String floor, String roomNo);
}
