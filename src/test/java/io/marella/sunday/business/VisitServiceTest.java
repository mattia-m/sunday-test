package io.marella.sunday.business;

import io.marella.sunday.integration.VisitRepository;
import io.marella.sunday.model.Visit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.BadRequestException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class VisitServiceTest {

    @InjectMocks
    VisitService testSubject;

    @Mock
    VisitRepository repository;

    @Test
    void updatePatientShouldThowIfIdNotEqualsToPatientId() {

        Visit a = new Visit();

        assertThrows(BadRequestException.class, () -> testSubject.updateVisit("aaa", a));
    }

    @Test
    void updatePatientShouldNotThrowIfIdMatches() {
        Visit a = new Visit();
        a.setVisitId("aaa");

        assertDoesNotThrow(() -> testSubject.updateVisit("aaa", a));
    }

}
