package models;

import java.util.ArrayList;
import java.util.List;

import play.db.ebean.Model.Finder;

import com.google.code.morphia.annotations.Property;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;


import controllers.MorphiaObject;

public class Organization {
	
	@Property("SchoolName")
	public String schoolName = null;
	
	@Property("CompanyName")
	public String orgName = null;
	
	@Property("Percentage")
	public float percent = 0;
	
	// a find helper to initiate queries.
	public static Finder<Long, Alumnus> find = new Finder<>(Long.class, Alumnus.class);
	
	/**
	 * Constructor
	 */
	public Organization() {
		// TODO Auto-generated constructor stub
	}
	
	public static List<Organization> getOrganizationDetails(String schoolName){
		
		if (MorphiaObject.datastore != null){
			
			List<Organization> alumniList = new ArrayList<Organization>();
			BasicDBObject getObject = new BasicDBObject();
			getObject.put("SchoolName", schoolName);
			DBCursor cursor = MorphiaObject.datastore.getCollection(Organization.class).find(getObject);
			while(cursor.hasNext()){
				Alumnus a = new Alumnus();
				
				DBObject dbObject = cursor.next();
				
				Object obj = dbObject.get("firstName");
				a.firstName = "";
				if(obj != null){
					a.firstName = obj.toString();
				}
				
				obj = dbObject.get("lastName");
				a.lastName = "";
				if(obj != null){
					a.lastName = obj.toString();
				}				
				
				a.headline = "";
				
				String headline = null;
				obj = dbObject.get("headline");				
				if(obj != null){
					headline = obj.toString();
				}
				
				if(!"".equals(headline))
				{
					int last = 0;
					
					int first = headline.indexOf(" at");
					if(first <= 0){
						first = headline.indexOf(",");
						if(first <= 0){
							first = -1;
						}else{
							last = first + 1;
						}
					}
					else{
						last = first + 3;
					}
					
					if(first == -1){
						a.position = headline;
						a.organization = "";
					}
					else{
						a.position = headline.substring(0, first);
						if(a.position == null){
							a.position = "";
						}
						
						a.organization = headline.substring(last);
						if(a.organization == null){
							a.organization = "";
						}
					}
					
				}
				
				a.schoolName = schoolName;
				//alumniList.add(a);
			}
			
			return alumniList;
		} 
		else 
		{
			return new ArrayList<Organization>();
		}
	}
}
