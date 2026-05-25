package complain_system.project.model;

public class Admin extends User {

    public Admin() {
		setRole(UserRole.ADMIN_DIRECTOR);
    }

    public Admin(Long id, String name, String email, String phone, String password) {
		super(id, name, email, phone, password, UserRole.ADMIN_DIRECTOR);
    }
}