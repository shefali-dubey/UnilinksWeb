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

@Entity("company")
public class Organization {
	
	@Property("SchoolName")
	public String schoolName = null;
	
	@Property("CompanyName")
	public String orgName = null;
	
	@Property("Percentage")
	public float percent = 0;
	
	// a find helper to initiate queries.
	public static Finder<Long, Organization> find = new Finder<>(Long.class, Organization.class);
	
	/**
	 * Constructor
	 */
	public Organization() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * This method gets organizations for which students of specified university are working.
	 * @param schoolName
	 * @return
	 */
	public static List<Organization> getOrganizationDetails(String schoolName){
		
		if (MorphiaObject.datastore != null){
			
			List<Organization> orgList = new ArrayList<Organization>();
			BasicDBObject getObject = new BasicDBObject();
			getObject.put("SchoolName", schoolName);
			DBCursor cursor = MorphiaObject.datastore.getCollection(Organization.class).find(getObject);
			
			// Iterate to get records
			while(cursor.hasNext()){
				Organization org = new Organization();
				
				DBObject dbObject = cursor.next();
				
				// Populate Organization objects with data obtained
				Object obj = dbObject.get("CompanyName");
				org.orgName = "";
				if(obj != null){
					org.orgName = obj.toString();
				}
				
				obj = dbObject.get("Percentage");
				org.percent = 0;
				if(obj != null){
					org.percent = Float.parseFloat(obj.toString());
				}
				
				orgList.add(org);				
			}
			
			return orgList;
		} 
		else 
		{
			return new ArrayList<Organization>();
		}
	}
}
