package complain_system.project.security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import complain_system.project.model.ComplaintCategory;
import complain_system.project.repository.ComplaintCategoryRepository;

@Component
public class ComplaintCategoryBootstrap implements CommandLineRunner {

    private final ComplaintCategoryRepository complaintCategoryRepository;

    public ComplaintCategoryBootstrap(ComplaintCategoryRepository complaintCategoryRepository) {
        this.complaintCategoryRepository = complaintCategoryRepository;
    }

    @Override
    public void run(String... args) {
        seedCategory("Electric");
        seedCategory("Water");
        seedCategory("Carpaenting");
    }

    private void seedCategory(String name) {
        String normalizedName = ComplaintCategory.normalize(name);
        if (!complaintCategoryRepository.existsByName(normalizedName)) {
            complaintCategoryRepository.save(new ComplaintCategory(normalizedName));
        }
    }
}