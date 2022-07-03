package io.marella.sunday.business;

import io.marella.sunday.integration.VisitRepository;
import io.marella.sunday.model.Visit;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.util.List;

@RequestScoped
public class VisitService {

    @Inject
    private VisitRepository visitRepository;

    public List<Visit> getVisits(String patientId, String visitType) {
        return visitRepository.getVisits(patientId, visitType);
    }

    public Visit getVisit(String visitId) {
        return visitRepository.getVisit(visitId);
    }

    public Visit updateVisit(String visitId, Visit visit) {
        if (!StringUtils.equals(visit.getVisitId(), visitId)) {
            throw new BadRequestException("Trying to update wrong visit");
        }
        return visitRepository.createOrUpdate(visit);
    }

    public Visit createVisit(Visit visit) {
        return visitRepository.createOrUpdate(visit);
    }

    public void deleteVisit(String visitId) {
        visitRepository.deleteVisit(visitId);
    }
}
