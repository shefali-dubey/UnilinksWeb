package controllers;

import java.net.UnknownHostException;
import java.util.List;

import models.Alumnus;
import models.Organization;
import models.Salary;
import play.Logger;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;

public class MorphiaObject {
	private static String dbname = "test";
	
	static public Mongo mongo;
	static public Morphia morphia;
	static public Datastore datastore;
	static public boolean cleanup = false;
	
	static {
		
		try {
			Logger.debug("** onStart **");
			MorphiaObject.mongo = new Mongo("127.0.0.1", 27017);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		MorphiaObject.morphia = new Morphia();
		MorphiaObject.datastore = MorphiaObject.morphia.createDatastore(
				MorphiaObject.mongo, dbname);
		
		// Ensure Index
		MorphiaObject.datastore.ensureIndexes();
//		MorphiaObject.datastore.getCollection(Alumnus.class).ensureIndex("SchoolName");
//		MorphiaObject.datastore.getCollection(Organization.class).ensureIndex("SchoolName");
//		MorphiaObject.datastore.getCollection(Salary.class).ensureIndex("SchoolName");
		
		MorphiaObject.datastore.ensureCaps();

	

		Logger.debug("** Morphia datastore: " + MorphiaObject.datastore.getDB());
	}
	
	
	/**
	 * Cleanup method.
	 */
	public static void cleanupDb() {
		List<Alumnus> alumni = MorphiaObject.datastore.find(Alumnus.class).asList();
		for (Alumnus a : alumni) {
			BasicDBObject delete = new BasicDBObject();
			delete.put("firstName", a.firstName);
			delete.put("lastName", a.lastName);
			MorphiaObject.datastore.getCollection(Alumnus.class).remove(delete);
		}
		
		List<Organization> org = MorphiaObject.datastore.find(Organization.class).asList();
		for (Organization a : org) {
			BasicDBObject delete = new BasicDBObject();
			delete.put("CompanyName", a.orgName);
			MorphiaObject.datastore.getCollection(Organization.class).remove(delete);
		}
		
		List<Salary> sal = MorphiaObject.datastore.find(Salary.class).asList();
		for (Salary a : sal) {
			BasicDBObject delete = new BasicDBObject();
			delete.put("SchoolName", a.schoolName);
			MorphiaObject.datastore.getCollection(Salary.class).remove(delete);
		}
	}
	
}
