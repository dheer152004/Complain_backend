package complain_system.project.role;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import complain_system.project.role.model.Role;
import complain_system.project.role.repository.RoleRepository;

@Component
public class RoleBootstrap implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleBootstrap(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        seedRole("SUPER_ADMIN", "Super administrator who manages branches and system configuration.");
        seedRole("ADMIN_DIRECTOR", "Top-level administrator who can manage roles and users.");
        seedRole("WARDEN", "Warden role for complaint handling and approvals.");
        seedRole("STUDENT", "Student role for complaint submission.");
        seedRole("MAINTENANCE_CELL_ADMIN", "Maintenance cell admin who creates and manages job cards.");
        seedRole("STORE_MANAGER", "Store manager who manages inventory and store items.");
    }

    private void seedRole(String name, String description) {
        roleRepository.findByName(name).orElseGet(() -> roleRepository.save(new Role(name, description)));
    }
}