package complain_system.project.location.dto;

import jakarta.validation.constraints.NotBlank;

public class LocationRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String floor;

    @NotBlank
    private String roomNo;

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
