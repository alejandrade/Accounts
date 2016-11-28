package com.owlberto.utils.filters;

import java.sql.SQLException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.gigamog.HttpUtil.CustomResponses;
import com.owlberto.exceptions.UnauthorizedException;

public class AccountsExceptionMapper implements ExceptionMapper<Exception>{
	boolean debug = true;
	@Override
	public Response toResponse(Exception exception) {
		// TODO Auto-generated method stub
		Response resp = null;
		
		
		if(debug){
			if(exception instanceof SQLException){
				SQLException sql = (SQLException)exception;
				resp = CustomResponses.sqlErrorResponse(sql.getErrorCode(), sql.getSQLState());
			} else if(exception instanceof WebApplicationException){
				WebApplicationException webApp = (WebApplicationException)exception;
				resp = CustomResponses.notFoundResponse(((webApp.getMessage()!=null)? "wrong path":"wrong method"));
			}else if(exception instanceof NullPointerException){
				resp = CustomResponses.badRequestResponse("malformed json " + exception.getMessage() );
			}else if(exception instanceof UnauthorizedException){
				resp = CustomResponses.unauthorizedResponse(exception.getMessage());
			}else{
				resp = CustomResponses.serverErrorResponse("jersey error " + exception.getMessage());
			}
		}
			
			
		
		return resp;
	}
}
