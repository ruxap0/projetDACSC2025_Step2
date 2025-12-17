package hepl.DACSC.model.viewmodel;

public class DoctorSearchVM {
    private Integer doctorId;
    private String login;
    private String password;

    public DoctorSearchVM() {}

    public DoctorSearchVM(Integer doctorId, String login, String password) {
        this.doctorId = doctorId;
        this.login = login;
        this.password = password;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
