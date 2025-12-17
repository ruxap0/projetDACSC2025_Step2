package hepl.DACSC.protocol.reponses;

import hepl.DACSC.protocol.Reponse;

import java.util.ArrayList;

public class LoginReponse implements Reponse {
    private boolean success;

    public LoginReponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
