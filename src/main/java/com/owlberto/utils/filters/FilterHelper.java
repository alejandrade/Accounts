package com.owlberto.utils.filters;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.jose4j.jwt.JwtClaims;

import com.gigamog.cryptorator.exception.JwtExpiredException;
import com.gigamog.jwt.Jwt;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.owlberto.Accounts.constants.ServiceKeys;
import com.owlberto.exceptions.UnauthorizedException;
import com.owlberto.utils.OwlMongo;

public class FilterHelper {

	public static void addCORS(HttpServletResponse resp) {
		resp.setHeader("Server", "Microsoft-IIS/7.0");
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		resp.setHeader("Access-Control-Allow-Credentials", "true");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		resp.setHeader("Access-Control-Max-Age", "3000");

	}


	public static void changeContent(HttpServletResponse resp, String content) throws IOException {
		resp.setContentLength(content.length());
		OutputStream os = resp.getOutputStream();
		os.write(content.getBytes());
		os.flush();
		os.close();
	}
	public static void isValidJwt(HttpServletRequest req) {

		String authHeader = req.getHeader("Auth");
		if (authHeader == null || authHeader.equals(""))
			throw new UnauthorizedException("missing auth header");

		String cleanJWT = authHeader.replace(authHeader, "JWT");
		if (cleanJWT == null || cleanJWT.equals(""))
			throw new UnauthorizedException("missing key");

		Jwt jwt = new Jwt();

		try {
			final JwtClaims claim = jwt.readJWT(ServiceKeys.JWTPASSWORD, cleanJWT);

			new Thread(() -> {
				MongoClient mc = OwlMongo.getMongo();
				Document doc = Document.parse(claim.toJson());
				mc.getDatabase(OwlMongo.OWLBERTODB).getCollection(OwlMongo.accessLog).insertOne(doc);
				mc.close();
			}).start();
		} catch (JwtExpiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void isSessionBanned(HttpServletRequest req) {
		String authHeader = req.getHeader("Auth");
		
		if (authHeader == null || authHeader.equals(""))
			throw new UnauthorizedException("missing auth header");
		
		String cleanJWT = authHeader.replace(authHeader, "JWT");
		if (cleanJWT == null || cleanJWT.equals(""))
			throw new UnauthorizedException("missing key");

		BasicDBObject query = new BasicDBObject("jwt", cleanJWT);
		MongoClient mc = OwlMongo.getMongo();
		Document docs = mc.getDatabase(OwlMongo.OWLBERTODB).getCollection(OwlMongo.closedJWtCol).find(query).first();
		
		if(docs != null)
			throw new UnauthorizedException("key has been banned");
		
		
		
		
		mc.close();
		

	}

}
