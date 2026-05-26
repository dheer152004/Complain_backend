package complain_system.project.branch.dto;

public class UniBranchResponse {

    private Long branchId;
    private String name;
    private Long adminDirectorId;

    public UniBranchResponse() {}

    public UniBranchResponse(Long branchId, String name, Long adminDirectorId) {
        this.branchId = branchId;
        this.name = name;
        this.adminDirectorId = adminDirectorId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public String getName() {
        return name;
    }

    public Long getAdminDirectorId() {
        return adminDirectorId;
    }
}
