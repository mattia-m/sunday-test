package io.marella.sunday.api.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.marella.sunday.business.VisitService;
import io.marella.sunday.model.Visit;
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

@Path("v1/visits")
@Produces(MediaType.APPLICATION_JSON)
@Tags(value = @Tag(name = "Visits", description = "Provided API used to handle Patient visits"))
@RequestScoped
public class VisitResource {

    @Inject
    private VisitService service;

    ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    private void setMapperProperty() {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @GET
    @Operation(summary = "returns all visit matching provided query param filters")
    @ApiResponse(responseCode = "200", description = "data succesfully retrieved",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class)))
    public Response getVisits(@Parameter(description = "patient id") @QueryParam(value = "patientId") String patientId,
                              @Parameter(description = "Visit Type") @QueryParam(value = "visitType") String visitType) {
        return Response.ok(service.getVisits(patientId, visitType)).build();
    }

    @GET
    @Operation(summary = "returns a visit by id")
    @Path("/{visitId}")
    @ApiResponse(responseCode = "200", description = "data succesfully retrieved",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class)))
    public Response getVisit(@Parameter(description = "visit id") @PathParam(value = "visitId") String visitId) throws JsonProcessingException {

        return Response.ok(mapper.writeValueAsString(service.getVisit(visitId))).build();

    }

    @PUT
    @Operation(summary = "update a visit by id")
    @Path("/{visitId}")
    @ApiResponse(responseCode = "200", description = "data succesfully retrieved",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class)))
    public Response updateVisit(@Parameter(description = "visit id") @PathParam(value = "visitId") String visitId,
                                String visit) throws JsonProcessingException {
        return Response.ok(service.updateVisit(visitId, mapper.readValue(visit, Visit.class))).build();
    }

    @POST
    @Operation(summary = "create a visit, automatically generating an id")
    @ApiResponse(responseCode = "200", description = "data succesfully created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Visit.class)))
    public Response createVisit(String visit) throws JsonProcessingException {

        return Response.ok(service.createVisit(mapper.readValue(visit, Visit.class))).build();
    }

    @DELETE
    @Operation(summary = "delete a visit by id")
    @Path("/{visitId}")
    @ApiResponse(responseCode = "200", description = "data succesfully deleted")
    public Response deleteVisit(@Parameter(description = "visit id") @PathParam(value = "visitId") String visitId) {
        service.deleteVisit(visitId);
        return Response.ok().build();
    }
}
