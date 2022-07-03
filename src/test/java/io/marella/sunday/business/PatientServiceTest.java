package io.marella.sunday.business;

import io.marella.sunday.integration.PatientRepository;
import io.marella.sunday.model.Patient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.BadRequestException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @InjectMocks
    PatientService testSubject;

    @Mock
    PatientRepository repository;

    @Test
    void updatePatientShouldThowIfIdNotEqualsToPatientId() {

        Patient a = new Patient();

        assertThrows(BadRequestException.class, () -> testSubject.updatePatient("aaa", a));
    }

    @Test
    void updatePatientShouldNotThrowIfIdMatches() {
        Patient a = new Patient();
        a.setSocialSecurityId("aaa");

        assertDoesNotThrow(() -> testSubject.updatePatient("aaa", a));
    }

}
