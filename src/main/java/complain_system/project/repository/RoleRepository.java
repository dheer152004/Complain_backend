package complain_system.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import complain_system.project.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);

    boolean existsByName(String name);
}