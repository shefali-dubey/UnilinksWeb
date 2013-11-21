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

/**
 * 
 * @author shefali
 *
 */
@Entity("salary")
public class Salary {
	
	@Property("SchoolName")
	public String schoolName = null;
	
	@Property("AverageSal")
	public float aveSalary = 0;
	
	@Property("Branch")
	public String branch = null;
	

	// a find helper to initiate queries.
	public static Finder<Long, Salary> find = new Finder<>(Long.class, Salary.class);
	
	/**
	 * Constructor
	 */
	public Salary() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Get department-wise average salaries of alumni of specified school.
	 * @param schoolName
	 * @return
	 */
	public static List<Salary> getSalaryData(String schoolName){
		
		if (MorphiaObject.datastore != null){
			
			List<Salary> salaryList = new ArrayList<Salary>();
			
			BasicDBObject getObject = new BasicDBObject();
			getObject.put("SchoolName", schoolName);
			DBCursor cursor = MorphiaObject.datastore.getCollection(Salary.class).find(getObject);
			
			// Iterate over query results
			while(cursor.hasNext()){
				Salary sal = new Salary();
				
				DBObject dbObject = cursor.next();
				
				// Set Branch
				sal.branch = "";
				Object obj = dbObject.get("Branch");			
				if(obj != null){
					sal.branch = obj.toString();
				}
				
				// Set Average Salary
				sal.aveSalary = 0;
				obj = dbObject.get("AverageSal");				
				if(obj != null){
					sal.aveSalary = Integer.parseInt(obj.toString());
				}		
				
				// Set school name
				sal.schoolName = schoolName;
				salaryList.add(sal);
			}
			
			return salaryList;
		} 
		else 
		{
			return new ArrayList<Salary>();
		}

	}

}
