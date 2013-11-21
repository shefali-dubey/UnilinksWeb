import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.mongodb.BasicDBObject;

import controllers.MorphiaObject;

import models.Alumnus;
import models.Organization;
import models.Salary;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;


/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {	
	
	@Test
	public void testAlumnus(){
			
		List<Alumnus> alumniList = Alumnus.getAlumni("San Jose State University");
		assertThat(alumniList).isNotNull();
		assertThat(alumniList).isNotEmpty();
		
		for(Alumnus alum : alumniList){
			assertThat(alum.schoolName).isEqualToIgnoringCase("San Jose State University");
		}
	}

	@Test
	public void testOrganizationData(){
		
		List<Organization> orgList = Organization.getOrganizationDetails("San Jose State University");
		assertThat(orgList).isNotNull();
		assertThat(orgList).isNotEmpty();
		
		for(Organization org : orgList){
			assertThat(org.schoolName).isEqualToIgnoringCase("San Jose State University");
		}
	}
	
	@Test
	public void testSalaryData(){
				
		List<Salary> salList = Salary.getSalaryData("San Jose State University");
		assertThat(salList).isNotNull();
		assertThat(salList).isNotEmpty();
		
		for(Salary sal : salList){
			assertThat(sal.schoolName).isEqualToIgnoringCase("San Jose State University");
			assertThat(sal.aveSalary).isNull();
		}
	}

}
