package controllers;

import java.net.UnknownHostException;
import java.util.List;

import models.Alumnus;
import play.Logger;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class MorphiaObject {
	private static String dbname = "test";
	
	static public Mongo mongo;
	static public Morphia morphia;
	static public Datastore datastore;
	
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
		MorphiaObject.datastore.ensureIndexes();
		MorphiaObject.datastore.ensureCaps();

		// cleanupDb();

		Logger.debug("** Morphia datastore: " + MorphiaObject.datastore.getDB());
	}
	
	
	/**
	 * Cleanup method.
	 */
	private static void cleanupDb() {
		List<Alumnus> alumni = MorphiaObject.datastore.find(Alumnus.class).asList();
		for (Alumnus a : alumni) {
			BasicDBObject delete = new BasicDBObject();
			delete.put("firstName", a.firstName);
			delete.put("lastName", a.lastName);
			MorphiaObject.datastore.getCollection(Alumnus.class).remove(delete);
		}
	}
	
}
