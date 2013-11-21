package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Alumnus;
import models.Organization;
import models.Salary;
import models.Search;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Application extends Controller {
	
	// encapsulates an HTML form definition, including validation constraints
	static Form<Search> searchForm = Form.form(Search.class);

	/**
	 * Home Page
	 * @return
	 */
	public static Result home() {
	    return ok(views.html.search.render(searchForm));
	}
	
	/**
	 * Search Alumni
	 * @return
	 */
	public static Result search(){
		
		// Get the filled form
		Form<Search> filledForm = searchForm.bindFromRequest();
		
		// Get university name to search
		String universityName = filledForm.get().universityName;
		
		if(filledForm.hasErrors()){
			  return badRequest(views.html.search.render(searchForm));
		}
		else{
			
			// Get the list of alumni of specified university from MongoDB
			List<Alumnus> alumniList = Alumnus.getAlumni(universityName);
			
			// Get the list of organizations for which alumni of specified university are working
			List<Organization> orgList = Organization.getOrganizationDetails(universityName);
			
			// Get salary data about alumni of this university
			List<Salary> salaryList = Salary.getSalaryData(universityName);
			
			Map<String,Integer> companyToStudentCount = new HashMap<>();
			companyToStudentCount.put("microsoft",15);
			companyToStudentCount.put("google",20);
			companyToStudentCount.put("apple",28);
			companyToStudentCount.put("vmware",12);
			companyToStudentCount.put("salesforce",5);
			companyToStudentCount.put("intuit",8);
			companyToStudentCount.put("qualcomm",12);

			// Render this data
			//return ok(views.html.table.render(universityName, alumniList, orgList, salaryList));
			return ok(views.html.table.render(universityName, alumniList, companyToStudentCount));
		}
		
	}

}
