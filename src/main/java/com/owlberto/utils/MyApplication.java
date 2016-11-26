package com.owlberto.utils;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/")
public class MyApplication extends ResourceConfig {

    
    public MyApplication() {
       // packages(TheEndpoints.class.getPackage().getName());
        register(GsonJerseyProvider.class);
  
	
    }
    
}
