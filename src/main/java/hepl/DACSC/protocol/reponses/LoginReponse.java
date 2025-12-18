package hepl.DACSC.protocol.reponses;

import hepl.DACSC.protocol.Reponse;

import java.util.ArrayList;

public class LoginReponse implements Reponse {
    private boolean success;
    private int doctorID;

    public LoginReponse(boolean success, int doctorID) {
        this.success = success;
        this.doctorID = doctorID;
    }

    public boolean isSuccess() {
        return success;
    }
}
