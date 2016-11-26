package com.owlberto.utils.filters;


import java.io.IOException;
import java.io.OutputStream;

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

		FilterHelper.addCORS(resp);
		if (req.getMethod().equals("OPTIONS")) {
			FilterHelper.removeContent(resp);
		}
		
		
		
		
		chain.doFilter(request, response);
		
	}


	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}