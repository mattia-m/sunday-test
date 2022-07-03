package io.marella.sunday.model;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.marella.sunday.producer.InstantDeserializer;
import io.marella.sunday.producer.InstantSerializer;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;


@Entity(name = "visit")
public class Visit {
    @Id
    private String visitId;
    @JsonDeserialize(using = InstantDeserializer.class)
    @JsonSerialize(using = InstantSerializer.class)
    private Instant visitTime;
    @ManyToOne
    private Patient patient;
    @Enumerated(EnumType.STRING)
    private VisitType visitType;
    @Enumerated(EnumType.STRING)
    private VisitReason visitReason;
    @Column(length = 2000)
    private String history;

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public Instant getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(Instant visitTime) {
        this.visitTime = visitTime;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public VisitType getVisitType() {
        return visitType;
    }

    public void setVisitType(VisitType visitType) {
        this.visitType = visitType;
    }

    public VisitReason getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(VisitReason visitReason) {
        this.visitReason = visitReason;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return Objects.equals(visitId, visit.visitId) && Objects.equals(visitTime, visit.visitTime) && Objects.equals(patient, visit.patient) && visitType == visit.visitType && visitReason == visit.visitReason && Objects.equals(history, visit.history);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitId, visitTime, patient, visitType, visitReason, history);
    }
}
