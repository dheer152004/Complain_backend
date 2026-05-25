package complain_system.project.user.model;

import java.util.Locale;

public class Student extends User {

    private String enrollmentNo;
    private boolean isHosteller;

    public Student() {
        setRole(UserRole.STUDENT);
    }

    public Student(Long id, String name, String email, String phone, String password, String enrollmentNo,
            boolean isHosteller) {
        super(id, name, email, phone, password, UserRole.STUDENT);
        setEmail(email);
        this.enrollmentNo = enrollmentNo;
        this.isHosteller = isHosteller;
    }

    @Override
    public void setEmail(String email) {
        if (email != null && !email.toLowerCase(Locale.ROOT).endsWith(".edu")) {
            throw new IllegalArgumentException("Student email must end with .edu");
        }
        super.setEmail(email);
    }

    public String getEnrollmentNo() {
        return enrollmentNo;
    }

    public void setEnrollmentNo(String enrollmentNo) {
        this.enrollmentNo = enrollmentNo;
    }

    public boolean isHosteller() {
        return isHosteller;
    }

    public void setHosteller(boolean hosteller) {
        isHosteller = hosteller;
    }
}
