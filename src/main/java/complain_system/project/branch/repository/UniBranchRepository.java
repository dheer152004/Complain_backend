package complain_system.project.branch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import complain_system.project.branch.model.UniBranch;

public interface UniBranchRepository extends JpaRepository<UniBranch, Long> {

    Optional<UniBranch> findByName(String name);

    boolean existsByName(String name);
}
