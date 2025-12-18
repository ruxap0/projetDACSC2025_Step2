package hepl.DACSC.protocol.requetes;

import hepl.DACSC.protocol.Requete;

public class AddPatientRequete implements Requete {
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
