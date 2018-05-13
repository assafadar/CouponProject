package exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionsHandler extends Exception implements ExceptionMapper<Throwable> {


	@Override
	public Response toResponse(Throwable exception) {
		
		if (exception instanceof ApplicationException){
			System.out.println("exception instanceof ApplicationException");
			return Response.status(700).entity(exception.getMessage()).build();
			
		}
		
		System.out.println("Returning 500 as the http status");
        return Response.status(701).entity("General failure").build();
        
	}

}
