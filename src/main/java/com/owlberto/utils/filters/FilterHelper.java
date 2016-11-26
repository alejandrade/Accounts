package com.owlberto.utils.filters;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.jose4j.jwt.JwtClaims;

import com.gigamog.cryptorator.exception.JwtExpiredException;
import com.gigamog.jwt.Jwt;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import com.owlberto.Accounts.constants.ServiceKeys;
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
	
	public static void removeContent(HttpServletResponse resp) throws IOException{
		resp.setHeader("Content-Length", "0");
		OutputStream os = resp.getOutputStream();
		os.write("".getBytes());
		os.flush();
		os.close();
	}
	
	
	public static boolean isValidJwt(HttpServletResponse reqs){
		String authHeader = reqs.getHeader("Auth");
		String cleanJWT = authHeader.replace(authHeader, "JWT");
		Jwt jwt = new Jwt();
		 boolean results = false;
		
		try {
			final JwtClaims claim = jwt.readJWT(ServiceKeys.JWTPASSWORD, cleanJWT);
			
			new Thread(()->{
				MongoClient mc = OwlMongo.getMongo();
				Document doc = Document.parse(claim.toJson());
				mc.getDatabase(OwlMongo.OWLBERTODB)
				.getCollection(OwlMongo.accessLog).insertOne(doc);
				mc.close();
			}).start();
			
			
			results = true;
		} catch (JwtExpiredException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}
	
	
	public static boolean isSessionBanned(HttpServletResponse reqs){
		String authHeader = reqs.getHeader("Auth");
		String cleanJWT = authHeader.replace(authHeader, "JWT");
		BasicDBObject query = new BasicDBObject("jwt", cleanJWT);
		MongoClient mc = OwlMongo.getMongo();
		 Document docs = mc.getDatabase(OwlMongo.OWLBERTODB)
				.getCollection(OwlMongo.closedJWtCol).find(query).first();
		 boolean results = docs != null;
		 mc.close();
		return results;

	}

}
