package complain_system.project.jobcard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import complain_system.project.jobcard.model.JobCard;

public interface JobCardRepository extends JpaRepository<JobCard, Long> {

    List<JobCard> findByComplaint_ComplainBy_IdOrderByDateDesc(Long userId);
}
