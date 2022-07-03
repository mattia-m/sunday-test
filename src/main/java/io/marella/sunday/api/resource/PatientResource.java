package io.marella.sunday.api.resource;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.marella.sunday.business.PatientService;
import io.marella.sunday.model.Patient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("v1/patients")
@Produces(MediaType.APPLICATION_JSON)
@Tags(value = @Tag(name = "patients", description = "Provided API used to handle Patient patients"))
@RequestScoped
public class PatientResource {

    @Inject
    private PatientService service;

    ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    private void setMapperProperty() {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @GET
    @Operation(summary = "returns all patient matching provided query param filters")
    @ApiResponse(responseCode = "200", description = "data succesfully retrieved",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Patient.class)))
    public Response getpatients(@Parameter(description = "surname") @QueryParam(value = "surname") String surname) throws JsonProcessingException {
        return Response.ok(mapper.writeValueAsString(service.getPatients(surname))).build();
    }

    @GET
    @Operation(summary = "returns a patient by id")
    @Path("/{patientId}")
    @ApiResponse(responseCode = "200", description = "data succesfully retrieved",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Patient.class)))
    public Response getpatient(@Parameter(description = "patient id") @PathParam(value = "patientId") String patientId) throws JsonProcessingException {
        return Response.ok(mapper.writeValueAsString(service.getPatient(patientId))).build();
    }

    @PUT
    @Operation(summary = "update a patient by id")
    @Path("/{patientId}")
    @ApiResponse(responseCode = "200", description = "data succesfully retrieved",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Patient.class)))
    public Response updatepatient(@Parameter(description = "patient id") @PathParam(value = "patientId") String patientId,
                                  String patientString) throws JsonProcessingException {
        Patient patient = mapper.readValue(patientString, Patient.class);
        return Response.ok(service.updatePatient(patientId, patient)).build();
    }

    @POST
    @Operation(summary = "create a patient")
    @ApiResponse(responseCode = "200", description = "data succesfully created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Patient.class)))
    public Response createpatient(String patientString) throws JsonProcessingException {

        mapper.registerModule(new JavaTimeModule());
        Patient patient = mapper.readerFor(Patient.class).readValue(patientString);

        return Response.ok(service.createPatient(patient)).build();
    }

    @DELETE
    @Operation(summary = "delete a patient by id")
    @Path("/{patientId}")
    @ApiResponse(responseCode = "200", description = "data succesfully deleted")
    public Response deletepatient(@Parameter(description = "patient id") @PathParam(value = "patientId") String patientId) {
        service.deletePatient(patientId);
        return Response.ok().build();
    }

}
