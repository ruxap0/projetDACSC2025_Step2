package hepl.DACSC.protocol.requetes;

import hepl.DACSC.model.entity.Patient;
import hepl.DACSC.protocol.Requete;

import java.time.LocalDate;

public class SearchConsultationRequete implements Requete {
    private int idDoctor;
    private String patient;
    private LocalDate dateConsultation;

    public SearchConsultationRequete(int idDoctor, String patient, LocalDate dateConsultation) {
        this.idDoctor = idDoctor;
        this.patient = patient;
        this.dateConsultation = dateConsultation;
    }

    public SearchConsultationRequete() {}

    public void setIdDoctor(int idDoctor) {
        this.idDoctor = idDoctor;
    }

    public int getIdDoctor() {
        return this.idDoctor;
    }

    public void setPatientName(String name)
    {
        this.patient = name;
    }

    public void setDateConsultation(LocalDate dateConsultation)
    {
        this.dateConsultation = dateConsultation;
    }

    public String getPatient() {
        return patient;
    }

    public LocalDate getDateConsultation() {
        return dateConsultation;
    }
}
