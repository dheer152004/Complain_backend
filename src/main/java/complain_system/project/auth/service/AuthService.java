package complain_system.project.auth.service;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import complain_system.project.auth.dto.AuthResponse;
import complain_system.project.auth.dto.RegisterRequest;
import complain_system.project.auth.dto.LoginRequest;
import complain_system.project.auth.dto.LogoutResponse;
import complain_system.project.role.dto.RoleRequest;
import complain_system.project.role.dto.RoleResponse;
import complain_system.project.branch.model.UniBranch;
import complain_system.project.branch.repository.UniBranchRepository;
import complain_system.project.role.model.Role;
import complain_system.project.user.model.User;
import complain_system.project.user.model.UserRole;
import complain_system.project.role.repository.RoleRepository;
import complain_system.project.user.repository.UserRepository;
import complain_system.project.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UniBranchRepository uniBranchRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository,
            UniBranchRepository uniBranchRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.uniBranchRepository = uniBranchRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new IllegalArgumentException("Email already registered");
        }

        Set<Role> roles = resolveStudentRoles(request.getRoles());
        UniBranch branch = uniBranchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new IllegalArgumentException("Branch not found"));

        User user = new User();
        user.setName(request.getName());
        user.setEmail(normalizedEmail);
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setBranch(branch);
        user.setRoles(roles);
        user.setRole(resolveLegacyRole(roles));

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);

        return new AuthResponse(token, savedUser.getId(), savedUser.getName(), savedUser.getEmail(),
                savedUser.getRoles().stream().map(Role::getName).collect(Collectors.toCollection(LinkedHashSet::new)));
    }

    public AuthResponse login(LoginRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase(Locale.ROOT);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(normalizedEmail, request.getPassword()));

        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        String token = jwtService.generateToken(user);

        return new AuthResponse(token, user.getId(), user.getName(), user.getEmail(),
                user.getRoles().stream().map(Role::getName).collect(Collectors.toCollection(LinkedHashSet::new)));
    }

    public LogoutResponse logout() {
        return new LogoutResponse("Logged out successfully");
    }

    public RoleResponse createRole(RoleRequest request) {
        String roleName = Role.normalize(request.getName());
        if (roleRepository.existsByName(roleName)) {
            throw new IllegalArgumentException("Role already exists");
        }

        Role role = new Role(roleName, request.getDescription());
        Role savedRole = roleRepository.save(role);
        return new RoleResponse(savedRole.getId(), savedRole.getName(), savedRole.getDescription());
    }

    public Set<RoleResponse> getRoles() {
        return roleRepository.findAll().stream()
                .map(role -> new RoleResponse(role.getId(), role.getName(), role.getDescription()))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Set<Role> resolveStudentRoles(Set<String> requestedRoles) {
        Set<String> roleNames = requestedRoles == null || requestedRoles.isEmpty() ? Set.of("STUDENT") : requestedRoles;
        Set<String> normalizedRoleNames = roleNames.stream()
                .map(Role::normalize)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        if (normalizedRoleNames.size() != 1 || !normalizedRoleNames.contains("STUDENT")) {
            throw new IllegalArgumentException("Registration is allowed only for STUDENT");
        }

        Set<Role> resolvedRoles = new LinkedHashSet<>();
        Role studentRole = roleRepository.findByName("STUDENT")
                .orElseThrow(() -> new IllegalArgumentException("STUDENT role not found"));
        resolvedRoles.add(studentRole);
        return resolvedRoles;
    }

    private UserRole resolveLegacyRole(Set<Role> roles) {
        for (Role role : roles) {
            if (role == null || role.getName() == null) {
                continue;
            }
            try {
                return UserRole.valueOf(role.getName());
            } catch (IllegalArgumentException ignored) {
                // Dynamic roles are allowed; the legacy enum remains for existing model helpers.
            }
        }
        return null;
    }
}
