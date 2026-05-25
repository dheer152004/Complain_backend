package complain_system.project.security;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import complain_system.project.role.model.Role;
import complain_system.project.user.model.User;
import complain_system.project.user.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                mapAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> mapAuthorities(User user) {
        Set<String> authorityNames = new LinkedHashSet<>();
        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            authorityNames.addAll(user.getRoles().stream()
                    .map(Role::getName)
                    .map(role -> "ROLE_" + role)
                    .collect(Collectors.toSet()));
        } else if (user.getRole() != null) {
            authorityNames.add("ROLE_" + user.getRole().name());
        }

        return authorityNames.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }
}