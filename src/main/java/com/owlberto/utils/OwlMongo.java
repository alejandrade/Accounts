package com.owlberto.utils;

import java.util.Arrays;
import java.util.List;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class OwlMongo {

	private static final String userName = "owladmin"; // the user name
	private static final String database = "owlberto"; // the name of the
														// database in // which
														// the user is defined
	private static final char[] password = "123".toCharArray();

	public static final String OWLBERTODB = database;
	public static final String closedJWtCol = "closed_jwt";
	public static final String accessLog = "access_log";
	
	
	static MongoCredential credential = MongoCredential.createCredential(database, userName, password); // user
																										// "myadmin"
																										// on
																										// admin
																										// database

	static List<ServerAddress> hosts = Arrays.asList(new ServerAddress("qmongo.bandura.cc", 27017),
			new ServerAddress("qmongo.bandura.cc", 27018), new ServerAddress("qmongo.bandura.cc", 27019));

	public static MongoClient getMongo() {
		return new MongoClient(hosts, Arrays.asList(credential));
	}

}
