package complain_system.project.service;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import complain_system.project.dto.AuthResponse;
import complain_system.project.dto.RegisterRequest;
import complain_system.project.dto.LoginRequest;
import complain_system.project.dto.LogoutResponse;
import complain_system.project.dto.RoleRequest;
import complain_system.project.dto.RoleResponse;
import complain_system.project.model.Role;
import complain_system.project.model.User;
import complain_system.project.model.UserRole;
import complain_system.project.repository.RoleRepository;
import complain_system.project.repository.UserRepository;
import complain_system.project.security.JwtService;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse register(RegisterRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new IllegalArgumentException("Email already registered");
        }

        Set<Role> roles = resolveRoles(request.getRoles());

        User user = new User();
        user.setName(request.getName());
        user.setEmail(normalizedEmail);
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
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

    private Set<Role> resolveRoles(Set<String> requestedRoles) {
        Set<String> roleNames = requestedRoles == null || requestedRoles.isEmpty() ? Set.of("STUDENT") : requestedRoles;

        Set<Role> resolvedRoles = new LinkedHashSet<>();
        for (String requestedRole : roleNames) {
            String roleName = Role.normalize(requestedRole);
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown role: " + requestedRole));
            resolvedRoles.add(role);
        }
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