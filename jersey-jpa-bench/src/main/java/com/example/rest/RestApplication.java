package com.example.rest;

import org.glassfish.jersey.server.ResourceConfig;
import com.example.rest.exception.GenericExceptionMapper;
import org.glassfish.jersey.jackson.JacksonFeature;

public class RestApplication extends ResourceConfig {
    public RestApplication(){
        packages("com.example.ressource");
        register(JacksonFeature.class);
        register(GenericExceptionMapper.class);
        // Validation feature if needed
    }
}
