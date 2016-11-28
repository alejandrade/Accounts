package com.owlberto.utils.filters;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@WebFilter(filterName = "AResponseFilter", urlPatterns = { "/*" })
public class ContainerFilter implements javax.servlet.Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse  resp = (HttpServletResponse) response;

		try{
	
			FilterHelper.addCORS(resp);
			if (req.getMethod().equals("OPTIONS")) {
				FilterHelper.changeContent(resp,"thanks for options call");
			}
			
			//filter helper should probably be a singleton, I'm checking for auth header in both
			//fix later
			FilterHelper.isSessionBanned(req);
			FilterHelper.isValidJwt(req);

		}catch(Exception e){
			FilterHelper.changeContent(resp,e.getMessage());
		}finally{
			chain.doFilter(request, response);
		}
	}


	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}