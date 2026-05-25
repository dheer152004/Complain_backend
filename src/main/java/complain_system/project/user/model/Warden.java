package complain_system.project.user.model;

public class Warden extends User {

    public Warden() {
        setRole(UserRole.WARDEN);
    }

    public Warden(Long id, String name, String email, String phone, String password) {
        super(id, name, email, phone, password, UserRole.WARDEN);
    }
}
