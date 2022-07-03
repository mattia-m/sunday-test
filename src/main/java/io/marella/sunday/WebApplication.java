package io.marella.sunday;

import io.marella.sunday.api.resource.PatientResource;
import io.marella.sunday.api.resource.VisitResource;
import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class WebApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();

        classes.add(VisitResource.class);
        classes.add(PatientResource.class);


        classes.add(OpenApiResource.class);

        return classes;
    }

}

