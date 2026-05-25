package complain_system.project.auth.bootstrap;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import complain_system.project.branch.model.UniBranch;
import complain_system.project.branch.repository.UniBranchRepository;
import complain_system.project.role.model.Role;
import complain_system.project.role.repository.RoleRepository;
import complain_system.project.user.model.User;
import complain_system.project.user.model.UserRole;
import complain_system.project.user.repository.UserRepository;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SuperAdminBootstrap implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UniBranchRepository uniBranchRepository;
    private final PasswordEncoder passwordEncoder;
    private final String email;
    private final String password;

    public SuperAdminBootstrap(UserRepository userRepository, RoleRepository roleRepository,
            UniBranchRepository uniBranchRepository, PasswordEncoder passwordEncoder,
            @Value("${super.admin.email:superadmin@system.com}") String email,
            @Value("${super.admin.password:admin123}") String password) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.uniBranchRepository = uniBranchRepository;
        this.passwordEncoder = passwordEncoder;
        this.email = email;
        this.password = password;
    }

    @Override
    public void run(String... args) {
        if (userRepository.existsByRoles_Name("SUPER_ADMIN")) {
            return;
        }

        Role superAdminRole = roleRepository.findByName("SUPER_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("SUPER_ADMIN",
                        "Super administrator who manages branches and system configuration.")));

        UniBranch systemBranch = uniBranchRepository.findByName("SYSTEM")
                .orElseGet(() -> uniBranchRepository.save(new UniBranch("SYSTEM")));

        User superAdmin = new User();
        superAdmin.setName("Super Admin");
        superAdmin.setEmail(email.trim().toLowerCase());
        superAdmin.setPhone("0000000000");
        superAdmin.setPassword(passwordEncoder.encode(password));
        superAdmin.setBranch(systemBranch);
        superAdmin.setRole(UserRole.SUPER_ADMIN);

        Set<Role> roles = new LinkedHashSet<>();
        roles.add(superAdminRole);
        superAdmin.setRoles(roles);

        userRepository.save(superAdmin);
    }
}
