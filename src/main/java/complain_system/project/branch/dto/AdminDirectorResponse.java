package complain_system.project.branch.dto;

import java.util.Set;

public class AdminDirectorResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private Long branchId;
    private Set<String> roles;

    public AdminDirectorResponse() {}

    public AdminDirectorResponse(Long id, String name, String email, String phone, Long branchId, Set<String> roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.branchId = branchId;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public Long getBranchId() {
        return branchId;
    }

    public Set<String> getRoles() {
        return roles;
    }
}
