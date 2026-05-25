package complain_system.project.model;

import java.util.Locale;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "locations", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "name", "floor", "room_no" })
})
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String floor;

    @Column(name = "room_no", nullable = false)
    private String roomNo;

    public Location() {
    }

    public Location(String name, String floor, String roomNo) {
        this.name = normalize(name);
        this.floor = normalize(floor);
        this.roomNo = normalize(roomNo);
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
        this.name = normalize(name);
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = normalize(floor);
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = normalize(roomNo);
    }

    public static String normalize(String value) {
        if (value == null) {
            return null;
        }
        return value.trim().toUpperCase(Locale.ROOT);
    }
}