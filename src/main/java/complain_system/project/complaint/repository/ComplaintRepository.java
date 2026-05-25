package complain_system.project.complaint.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import complain_system.project.complaint.model.Complaint;
import complain_system.project.user.model.User;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByComplainByOrderByCreatedTimeDesc(User complainBy);
}
