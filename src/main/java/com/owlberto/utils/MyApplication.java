package com.owlberto.utils;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import com.owlberto.endpoint.UserController;
import com.owlberto.utils.filters.AccountsExceptionMapper;
import com.owlberto.utils.filters.ContainerFilter;

@ApplicationPath("/")
public class MyApplication extends ResourceConfig {

    
    public MyApplication() {
        packages(UserController.class.getPackage().getName());
        register(GsonJerseyProvider.class);
        register(ContainerFilter.class);
        register(AccountsExceptionMapper.class);
  
	
    }
    
}
