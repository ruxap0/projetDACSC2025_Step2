package hepl.DACSC.protocol.requetes;

public class AddPatientRequete {
    private String firstName;
    private String lastName;

    public AddPatientRequete(String fN, String lN)
    {
        this.firstName = fN;
        this.lastName = lN;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
