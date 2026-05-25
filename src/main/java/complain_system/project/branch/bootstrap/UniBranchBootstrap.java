package complain_system.project.branch.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import complain_system.project.branch.model.UniBranch;
import complain_system.project.branch.repository.UniBranchRepository;

@Component
public class UniBranchBootstrap implements CommandLineRunner {

    private final UniBranchRepository uniBranchRepository;

    public UniBranchBootstrap(UniBranchRepository uniBranchRepository) {
        this.uniBranchRepository = uniBranchRepository;
    }

    @Override
    public void run(String... args) {
        seedBranch("UNI_BRANCH_1");
        seedBranch("UNI_BRANCH_2");
        seedBranch("UNI_BRANCH_3");
        seedBranch("UNI_BRANCH_4");
    }

    private void seedBranch(String name) {
        String normalizedName = UniBranch.normalize(name);
        if (!uniBranchRepository.existsByName(normalizedName)) {
            uniBranchRepository.save(new UniBranch(normalizedName));
        }
    }
}
