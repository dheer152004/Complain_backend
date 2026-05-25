package complain_system.project.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import complain_system.project.user.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByRoles_Name(String roleName);

    Optional<User> findFirstByRoles_Name(String roleName);
}
