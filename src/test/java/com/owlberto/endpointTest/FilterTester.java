package com.owlberto.endpointTest;

import java.net.URISyntaxException;

import org.junit.Test;

import com.gigamog.HttpUtil.HttpUtil;
import com.gigamog.HttpUtil.Resp;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.WebAppDescriptor;

public class FilterTester {
	
	protected AppDescriptor configure() {
		return new WebAppDescriptor.Builder().build();
	}
	
	@Test
	public void testPostAction() throws URISyntaxException {
		
		Resp resp = HttpUtil.HttpGet("http://localhost:8080/Accounts/user/test");
		System.out.println(resp.getBody());

	}

}
