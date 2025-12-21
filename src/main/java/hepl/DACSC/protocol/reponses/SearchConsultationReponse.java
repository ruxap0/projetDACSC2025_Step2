package hepl.DACSC.protocol.reponses;

import hepl.DACSC.model.entity.Consultation;
import hepl.DACSC.protocol.Reponse;

import java.util.ArrayList;

public class SearchConsultationReponse implements Reponse {
    private ArrayList<Consultation> consultations;

    public SearchConsultationReponse(ArrayList<Consultation> consultations) {
        this.consultations = consultations;
    }

    public ArrayList<Consultation> getConsultations()
    {
        return consultations;
    }
}
