package complain_system.project.branch.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import complain_system.project.branch.dto.UniBranchRequest;
import complain_system.project.branch.dto.UniBranchResponse;
import complain_system.project.branch.model.UniBranch;
import complain_system.project.branch.repository.UniBranchRepository;

@Service
public class BranchService {

    private final UniBranchRepository uniBranchRepository;

    public BranchService(UniBranchRepository uniBranchRepository) {
        this.uniBranchRepository = uniBranchRepository;
    }

    public UniBranchResponse createBranch(UniBranchRequest req) {
        String normalized = UniBranch.normalize(req.getName());
        if (uniBranchRepository.existsByName(normalized)) {
            throw new IllegalArgumentException("Branch already exists");
        }
        UniBranch branch = new UniBranch(req.getName());
        UniBranch saved = uniBranchRepository.save(branch);
        Long adminId = saved.getAdminDirector() == null ? null : saved.getAdminDirector().getId();
        return new UniBranchResponse(saved.getBranchId(), saved.getName(), adminId);
    }

    public List<UniBranchResponse> listBranches() {
        return uniBranchRepository.findAll().stream()
                .map(b -> new UniBranchResponse(b.getBranchId(), b.getName(), b.getAdminDirector() == null ? null : b.getAdminDirector().getId()))
                .collect(Collectors.toList());
    }
}
