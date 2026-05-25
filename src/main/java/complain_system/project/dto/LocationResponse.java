package complain_system.project.dto;

public class LocationResponse {

    private Long locationId;
    private String name;
    private String floor;
    private String roomNo;

    public LocationResponse() {
    }

    public LocationResponse(Long locationId, String name, String floor, String roomNo) {
        this.locationId = locationId;
        this.name = name;
        this.floor = floor;
        this.roomNo = roomNo;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
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