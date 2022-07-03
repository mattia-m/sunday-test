package io.marella.sunday.business;

import io.marella.sunday.integration.PatientRepository;
import io.marella.sunday.model.Patient;
import org.apache.commons.lang3.StringUtils;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import java.util.List;

@RequestScoped
public class PatientService {
    @Inject
    PatientRepository repository;

    public List<Patient> getPatients(String surname) {
        return repository.getPatients(surname);
    }

    public Patient getPatient(String patientId) {
        return repository.getPatient(patientId);
    }

    public Patient updatePatient(String patientId, Patient patient) {
        if (!StringUtils.equals(patientId, patient.getSocialSecurityId())) {
            throw new BadRequestException("Provided id is incoherent");
        }
        return repository.createOrUpdate(patient);
    }

    public Patient createPatient(Patient patient) {
        return repository.createOrUpdate(patient);
    }

    public void deletePatient(String patientId) {
        repository.deletePatient(patientId);
    }
}
