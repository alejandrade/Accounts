package com.owlberto.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/user")
public class UserController {
	
	@Path("/test")
	@GET
	public Response test(){
		return Response.ok().entity("welcome").build();
	}

}
