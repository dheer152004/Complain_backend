package complain_system.project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import complain_system.project.model.ComplaintCategory;

public interface ComplaintCategoryRepository extends JpaRepository<ComplaintCategory, Long> {

    Optional<ComplaintCategory> findByName(String name);

    boolean existsByName(String name);
}