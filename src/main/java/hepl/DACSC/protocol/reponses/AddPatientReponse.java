package hepl.DACSC.protocol.reponses;

import hepl.DACSC.protocol.Reponse;

public class AddPatientReponse implements Reponse {
    private Boolean isSuccess;
    private Integer idPatient;

    public AddPatientReponse(Boolean isSucces, Integer idPatient) {
        this.isSuccess = isSucces;
        this.idPatient = idPatient;
    }

    public Boolean IsSuccess() {
        return isSuccess;
    }

    public Integer getIdPatient() {
        return idPatient;
    }
}
