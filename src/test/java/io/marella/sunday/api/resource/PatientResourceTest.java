package io.marella.sunday.api.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.marella.sunday.business.PatientService;
import io.marella.sunday.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientResourceTest {

    @InjectMocks
    PatientResource testSubject;

    @Mock
    PatientService service;

    @Test
    void getShoulReturnOk() {
        when(service.getPatient("1")).thenReturn(new Patient());
        assertDoesNotThrow(() -> testSubject.getpatient("1"));
    }

    @Test
    void getListShoulReturnOk() {
        when(service.getPatients("a")).thenReturn(Collections.emptyList());
        assertDoesNotThrow(() -> testSubject.getpatients("a"));
    }

    @Test
    void updatePatienShoulReturnOk() {
        String patient = "{\n" +
                "\t\"socialSecurityId\": \"bbb\",\n" +
                "    \"name\": \"foo\",\n" +
                "    \"surname\": \"aDaa\",\n" +
                "    \"dateOfBirth\": \"2022-07-03 17:23:58\"\n" +
                "\t\n" +
                "}";

        when(service.updatePatient(eq("bbb"), any())).thenReturn(new Patient());
        assertDoesNotThrow(() -> testSubject.updatepatient("bbb", patient));
    }

    @Test
    void createPatientShouldReturOkWithProvidedPatient() throws JsonProcessingException {
        String patient = "{\n" +
                "\t\"socialSecurityId\": \"bbb\",\n" +
                "    \"name\": \"foo\",\n" +
                "    \"surname\": \"aDaa\",\n" +
                "    \"dateOfBirth\": \"2022-07-03 17:23:58\"\n" +
                "\t\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();

        Patient pat = mapper.readValue(patient, Patient.class);

        when(service.createPatient(pat)).thenReturn(pat);
        assertDoesNotThrow(() -> testSubject.createpatient("{\n" +
                "\t\"socialSecurityId\": \"bbb\",\n" +
                "    \"name\": \"foo\",\n" +
                "    \"surname\": \"aDaa\",\n" +
                "    \"dateOfBirth\": \"2022-07-03 17:23:58\"\n" +
                "\t\n" +
                "}"));

    }

    @Test
    void deleteShouldReturnOk() {
        assertDoesNotThrow(() -> testSubject.deletepatient("bbb"));

    }

}
