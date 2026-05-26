package complain_system.project.location.dto;

import jakarta.validation.constraints.NotBlank;

public class LocationRequest {

    @jakarta.validation.constraints.NotNull
    private Long branchId;

    @NotBlank
    private String name;

    @NotBlank
    private String floor;

    @NotBlank
    private String roomNo;

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }
}
