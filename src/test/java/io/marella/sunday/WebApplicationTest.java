package io.marella.sunday;

import org.junit.Test;

import javax.ws.rs.ApplicationPath;

import static org.junit.Assert.assertEquals;

public class WebApplicationTest {

    @Test
    public void classHaveCorrectPathAnnotation() {
        assertEquals("/api", WebApplication.class.getAnnotation(ApplicationPath.class).value());
    }

    @Test
    public void shouldIncludeAllNeededResource() {
        WebApplication application = new WebApplication();

        assertEquals(3, application.getClasses().size());
    }
}

