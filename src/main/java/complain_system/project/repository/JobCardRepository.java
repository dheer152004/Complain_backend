package complain_system.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import complain_system.project.model.JobCard;
import complain_system.project.model.User;

public interface JobCardRepository extends JpaRepository<JobCard, Long> {

    List<JobCard> findByComplaint_ComplainBy_IdOrderByDateDesc(Long userId);
}
