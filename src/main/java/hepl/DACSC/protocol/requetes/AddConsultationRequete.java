package hepl.DACSC.protocol.requetes;

import hepl.DACSC.protocol.Requete;

import java.time.LocalDate;
import java.time.LocalTime;

public class AddConsultationRequete implements Requete {
    private int doctorID;
    private LocalDate dateConsultation;
    private LocalTime heureConsultation;
    private Integer dureeConsultation;
    private Integer nbConsultation;

    public AddConsultationRequete(int idDoctor, LocalDate dateConsultation, LocalTime heureConsultation, Integer dureeConsultation, Integer nbConsultation) {
        this.doctorID = idDoctor;
        this.dateConsultation = dateConsultation;
        this.heureConsultation = heureConsultation;
        this.dureeConsultation = dureeConsultation;
        this.nbConsultation = nbConsultation;
    }

    public LocalDate getDateConsultation() {
        return dateConsultation;
    }

    public LocalTime getHeureConsultation() {
        return heureConsultation;
    }

    public Integer getDureeConsultation() {
        return dureeConsultation;
    }

    public Integer getNbConsultation() {
        return nbConsultation;
    }

    public Integer getDoctorID() {
        return doctorID;
    }
}

