package complain_system.project.branch.model;

import java.util.Locale;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import complain_system.project.user.model.User;

@Entity
@Table(name = "uni_branches", uniqueConstraints = { @UniqueConstraint(columnNames = "name") })
public class UniBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToOne
    @JoinColumn(name = "admin_director_user_id", unique = true)
    private User adminDirector;

    public UniBranch() {
    }

    public UniBranch(String name) {
        this.name = normalize(name);
    }

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
        this.name = normalize(name);
    }

    public User getAdminDirector() {
        return adminDirector;
    }

    public void setAdminDirector(User adminDirector) {
        this.adminDirector = adminDirector;
    }

    public static String normalize(String value) {
        if (value == null) {
            return null;
        }
        return value.trim().replace('-', '_').replace(' ', '_').toUpperCase(Locale.ROOT);
    }
}
