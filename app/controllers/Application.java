package controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mongodb.BasicDBObject;

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
	 * 
	 * @return
	 */
	public static Result home() {
		return ok(views.html.search.render(searchForm, "",
				new ArrayList<Alumnus>(), new ArrayList<Organization>(),
				new ArrayList<Salary>(), Alumnus.getAllSchools()));
	}

	/**
	 * Search Alumni
	 * 
	 * @return
	 */
	public static Result search() {

		// Get the filled form
		Form<Search> filledForm = searchForm.bindFromRequest();

		// Get university name to search
		String universityName = filledForm.get().universityName;

		if (filledForm.hasErrors()) {
			return badRequest(views.html.search.render(searchForm,
					universityName, new ArrayList<Alumnus>(),
					new ArrayList<Organization>(), new ArrayList<Salary>(),
					new ArrayList<String>()));
		} else {

			// Get the list of alumni of specified university from MongoDB
			List<Alumnus> alumniList = Alumnus.getAlumni(universityName);

			// Get the list of organizations for which alumni of specified
			// university are working
			List<Organization> orgList = Organization
					.getOrganizationDetails(universityName);

			// Get salary data about alumni of this university
			List<Salary> salaryList = Salary.getSalaryData(universityName);

			// Render this data
			// return ok(views.html.table.render(universityName, alumniList,
			// orgList, salaryList));
			return ok(views.html.search.render(searchForm, universityName,
					alumniList, orgList, salaryList, Alumnus.getAllSchools()));
		}
	}
}
