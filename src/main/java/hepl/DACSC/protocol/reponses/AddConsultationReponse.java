package hepl.DACSC.protocol.reponses;

public class AddConsultationReponse {
    private boolean success;

    public AddConsultationReponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
