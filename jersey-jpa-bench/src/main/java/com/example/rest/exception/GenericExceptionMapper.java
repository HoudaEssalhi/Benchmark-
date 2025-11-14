package com.example.rest.exception;

import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {
    @Override
    public Response toResponse(Throwable exception) {
        Map<String,Object> m = new HashMap<>();
        m.put("error", exception.getMessage());
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(m).build();
    }
}
