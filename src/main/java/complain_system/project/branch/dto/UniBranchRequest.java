package complain_system.project.branch.dto;

import jakarta.validation.constraints.NotBlank;

public class UniBranchRequest {

    @NotBlank
    private String name;

    public UniBranchRequest() {}

    public UniBranchRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
