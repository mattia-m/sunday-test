package io.marella.sunday.api.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.marella.sunday.business.VisitService;
import io.marella.sunday.model.Visit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitResourceTest {

    @InjectMocks
    VisitResource testSubject;

    @Mock
    VisitService service;

    @Test
    void getShoulReturnOk() {
        when(service.getVisit("1")).thenReturn(new Visit());
        assertDoesNotThrow(() -> testSubject.getVisit("1"));
    }

    @Test
    void getListShoulReturnOk() throws JsonProcessingException {
        when(service.getVisits("a", null)).thenReturn(Collections.emptyList());
        assertDoesNotThrow(() -> testSubject.getVisits("a", null));
    }

    @Test
    void updateVisitShoulReturnOk() throws JsonProcessingException {
        String visit = "{\n" +
                "    \"visitId\": \"123\",\n" +
                "    \"visitTime\": \"2022-01-03 09:19:08\"," +
                "    \"patient\": {\n" +
                "        \"socialSecurityId\": \"123456asd\",\n" +
                "        \"name\": \"foo\",\n" +
                "        \"surname\": \"aaa\",\n" +
                "        \"dateOfBirth\": \"2022-07-03 17:23:58\"\n" +
                "    },\n" +
                "    \"visitType\": \"AT_HOME\",\n" +
                "    \"visitReason\": \"FIRST\",\n" +
                "    \"history\": null\n" +
                "}";

        when(service.updateVisit(eq("123"), any())).thenReturn(new Visit());
        assertDoesNotThrow(() -> testSubject.updateVisit("123", visit));
    }

    @Test
    void createPatientShouldReturOkWithProvidedPatient() throws JsonProcessingException {
        String visit = "{\n" +
                "    \"visitId\": \"123\",\n" +
                "    \"visitTime\": \"2022-01-03 09:19:08\"," +
                "    \"patient\": {\n" +
                "        \"socialSecurityId\": \"123456asd\",\n" +
                "        \"name\": \"foo\",\n" +
                "        \"surname\": \"aaa\",\n" +
                "        \"dateOfBirth\": \"2022-07-03 17:23:58\"\n" +
                "    },\n" +
                "    \"visitType\": \"AT_HOME\",\n" +
                "    \"visitReason\": \"FIRST\",\n" +
                "    \"history\": null\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();

        Visit pat = mapper.readValue(visit, Visit.class);

        when(service.createVisit(pat)).thenReturn(pat);
        assertDoesNotThrow(() -> testSubject.createVisit("{\n" +
                "    \"visitId\": \"123\",\n" +
                "    \"visitTime\": \"2022-01-03 09:19:08\"," +
                "    \"patient\": {\n" +
                "        \"socialSecurityId\": \"123456asd\",\n" +
                "        \"name\": \"foo\",\n" +
                "        \"surname\": \"aaa\",\n" +
                "        \"dateOfBirth\": \"2022-07-03 17:23:58\"\n" +
                "    },\n" +
                "    \"visitType\": \"AT_HOME\",\n" +
                "    \"visitReason\": \"FIRST\",\n" +
                "    \"history\": null\n" +
                "}"));

    }

    @Test
    void deleteShouldReturnOk() {
        assertDoesNotThrow(() -> testSubject.deleteVisit("bbb"));
        verify(service, times(1)).deleteVisit("bbb");
    }

}
