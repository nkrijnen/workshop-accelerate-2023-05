package eu.luminis.workshop.smallsteps.api.support;

import eu.luminis.workshop.smallsteps.logic.domainservice.auth.AccessDeniedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AccessDeniedExceptionHandler implements ExceptionMapper<AccessDeniedException> {

    @Override
    public Response toResponse(AccessDeniedException e) {
        return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
    }
}
