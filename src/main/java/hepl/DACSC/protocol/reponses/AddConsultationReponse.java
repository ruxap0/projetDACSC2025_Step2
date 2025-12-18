package hepl.DACSC.protocol.reponses;

import hepl.DACSC.protocol.Reponse;

public class AddConsultationReponse implements Reponse {
    private boolean success;

    public AddConsultationReponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
