package complain_system.project.location.model;

import java.util.Locale;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import complain_system.project.branch.model.UniBranch;

@Entity
@Table(name = "locations", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "branch_id", "name" })
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private UniBranch branch;

    public Location() {
    }

    public Location(UniBranch branch, String name, String floor, String roomNo) {
        this.branch = branch;
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

    public UniBranch getBranch() {
        return branch;
    }

    public void setBranch(UniBranch branch) {
        this.branch = branch;
    }

    public static String normalize(String value) {
        if (value == null) {
            return null;
        }
        return value.trim().toUpperCase(Locale.ROOT);
    }
}
