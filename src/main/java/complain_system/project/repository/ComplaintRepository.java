package complain_system.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import complain_system.project.model.Complaint;
import complain_system.project.model.User;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByComplainByOrderByCreatedTimeDesc(User complainBy);
}