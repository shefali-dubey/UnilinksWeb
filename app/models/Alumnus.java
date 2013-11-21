package models;

import java.util.ArrayList;
import java.util.List;

import play.db.ebean.Model.Finder;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Property;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import controllers.MorphiaObject;

@Entity("students")
public class Alumnus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960054493435751132L;

	@Property("firstName")
	public String firstName = null;

	@Property("lastName")
	public String lastName = null;

	@Property("headline")
	public String headline = null;

	@Property("SchoolName")
	public String schoolName = null;

	public String position = null;

	public String organization = null;

	// a find helper to initiate queries.
	public static Finder<Long, Alumnus> find = new Finder<>(Long.class,
			Alumnus.class);

	/**
	 * Default Constructor
	 */
	public Alumnus() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * 
	 * @param firstName
	 * @param lastName
	 * @param orgName
	 * @param universityName
	 */
	/*
	 * public void Alumnus(String firstName, String lastName, String orgName,
	 * String universityName) { this.firstName = firstName; this.lastName =
	 * lastName; this.orgName = orgName; this.universityName = universityName; }
	 */

	/**
	 * This method gets the list of all alumnis.
	 * 
	 * @return
	 */
	public static List<String> getAllSchools() {
		List<String> aluminiSet = new ArrayList<>();
		List<Alumnus> alumni = MorphiaObject.datastore.find(Alumnus.class)
				.asList();
		for (Alumnus a : alumni) {
			if (!aluminiSet.contains(a.schoolName)) {
				aluminiSet.add(a.schoolName);
			}
		}
		return aluminiSet;
	}

	/**
	 * This method gets the list of all alumni of specified school from
	 * database.
	 * 
	 * @param schoolName
	 * @return
	 */
	public static List<Alumnus> getAlumni(String schoolName) {

		if (MorphiaObject.datastore != null) {

			List<Alumnus> alumniList = new ArrayList<Alumnus>();
			BasicDBObject getObject = new BasicDBObject();
			getObject.put("SchoolName", schoolName);
			DBCursor cursor = MorphiaObject.datastore.getCollection(
					Alumnus.class).find(getObject);
			while (cursor.hasNext()) {
				Alumnus a = new Alumnus();

				DBObject dbObject = cursor.next();

				Object obj = dbObject.get("firstName");
				a.firstName = "";
				if (obj != null) {
					a.firstName = obj.toString();
				}

				obj = dbObject.get("lastName");
				a.lastName = "";
				if (obj != null) {
					a.lastName = obj.toString();
				}

				a.headline = "";

				String headline = null;
				obj = dbObject.get("headline");
				if (obj != null) {
					headline = obj.toString();
				}

				if (!"".equals(headline)) {
					int last = 0;

					int first = headline.indexOf(" at");
					if (first <= 0) {
						first = headline.indexOf(",");
						if (first <= 0) {
							first = -1;
						} else {
							last = first + 1;
						}
					} else {
						last = first + 3;
					}

					if (first == -1) {
						a.position = headline;
						a.organization = "";
					} else {
						a.position = headline.substring(0, first);
						if (a.position == null) {
							a.position = "";
						}

						a.organization = headline.substring(last);
						if (a.organization == null) {
							a.organization = "";
						}
					}

				}

				a.schoolName = schoolName;
				alumniList.add(a);
			}

			return alumniList;
		} else {
			return new ArrayList<Alumnus>();
		}
	}

}
