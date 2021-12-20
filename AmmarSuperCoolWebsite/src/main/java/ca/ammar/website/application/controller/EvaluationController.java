/*
 * Syed Ammar Waseem
 * Assignment 3
 * SDNE(Software Development Network Engineering)
 *
 * Main controller for displaying forms, adding data to the database
 * editing and deleting from a database and also to display results
 * Responsible to use the curd operations from the database
 */

package ca.ammar.website.application.controller;

import ca.ammar.website.application.beans.Course;
import ca.ammar.website.application.beans.Evaluation;
import ca.ammar.website.application.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller()
@RequestMapping("/evaluation")
public class EvaluationController {

	private DatabaseAccess da;

	@Autowired
	private void databaseAccess (DatabaseAccess databaseAccess){
		this.da = databaseAccess;
	}

	/* Loads the index page
	 */
	@GetMapping("/")
	public String index() {
		return "evaluation/user/index";
	}

	/* Loads the form for adding evaluations and binds a new evaluation object
	 * to it, and gets courses from the database if they dont already exist
	 */
	@GetMapping("/evalForm")
	public String evalForm(Model model, HttpSession session) {

		// gets new courses if it doesn't already exist
		if (session.getAttribute("courses") == null) {
			session.setAttribute("courses", da.getCourses());
		}
		// adds a new evaluation object for the form to bind to
		model.addAttribute("evaluation", new Evaluation());

		// returns the form html
		return "evaluation/user/evalForm";
	}

	@GetMapping("/login")
	public String getLogin() {
		return "login/loginPage";
	}

	/* Loads the form for adding courses and binds a new course object to it.
	 */
	@GetMapping("/addCourse")
	public String courseForm(Model model) {
		// adds a new course object for the form to bind to
		model.addAttribute("course", new Course());
		// returns the course form
		return "evaluation/admin/courseForm";
	}

	/* process for courseForm, adds the new course object
	 */
	@PostMapping("/addCourse")
	public String courseProcess(@ModelAttribute Course course,
								HttpSession session) {
		// adds the course to the database
		da.addCourse(course);
		// refreshes the session attribute for courses
		session.setAttribute("courses", da.getCourses());
		// sends you back to the index page
		return "redirect:evaluation/";
	}

	/* Calculates Cumulative gpa and gathers total number of credits
	 * as well as saving an array with only completed courses and
	 * adding it the model
	 */
	@GetMapping("/gpaCalc")
	public String gpaCalc(Model model) {
		// stores every course in the database
		ArrayList<Course> completed = da.getCourses();
		// removes all the courses that are not completed
		completed.removeIf(a -> !(a.isComplete()));
		// adds the filtered arraylist to the model
		model.addAttribute("completed", completed);
		// the the array size is bigger than zero calculates the cumulative gpa
		// and calculates the number of credits
		if (completed.size() > 0) {
			// formula for getting the cumulative gpa
			double topSum = 0;
			int gradableCred = 0;
			for (Course a : completed) {
				gradableCred += a.getCredits();
				topSum += a.getCredits() * a.gradePoints();
			}
			// stores the cumulative gpa
			double cumulativeGpa = topSum / gradableCred;
			// adds total credits to the model
			model.addAttribute("totalCredits", gradableCred);
			// adds cumulative gpa to the model
			model.addAttribute("cumulativeGpa", cumulativeGpa);
		}
		// returns the gpaCalculator html
		return "evaluation/user/gpaCalculator";
	}

	/* get method to get to the evaluation table used once
	 * by deleteEvaluation and for testing purposes
	 * and gets evaluations from the database if they dont already exist
	 */
	@GetMapping("/evalResults")
	public String getEvalResults(HttpSession session) {
		// gets evaluation if it does not already exist
		if (session.getAttribute("evaluations") == null) {
			session.setAttribute("evaluations", da.getEvaluations());
		}
		// returns the results of evaluation
		return "evaluation/user/evalResults";
	}

	/* Handles both adding and editing evaluation
	 * and after leads to the evaluation results
	 * and sets a new session evaluations from the database
	 */
	@PostMapping("/evalResults")
	public String postEvalResults(@ModelAttribute Evaluation eval,
								  HttpSession session) {
		// stores the object of the boolean
		Object editCond = session.getAttribute("edit");

		// runs edit if it exists and its true
		if (editCond != null && (boolean) editCond) {
			da.editEvaluation(eval);
			session.setAttribute("edit", false);
		} else { // adds the evaluation
			da.addEvaluation(eval);
		}
		session.setAttribute("evaluations", da.getEvaluations());
		return "evaluation/user/evalResults";
	}

	/* performed when user clicks on edit, which takes the id of the eval
	 * as the pathVariable and is sent to eval form with the evaluation binded
	 */
	@GetMapping("/doEdit/{evalId}")
	public String editEvaluation(@PathVariable int evalId, Model model,
								 HttpSession session) {
		// gets the wanted evaluation object to edit and puts in the model
		model.addAttribute("evaluation", da.getEvaluation(evalId));
		// and makes the edit attribute in session true
		session.setAttribute("edit", true);
		// returns the evaluation form
		return "evaluation/user/evalForm";
	}


	/* performed when user clicks delete on a evaluation takes the id
	 * as the pathVariable and performs a database delete
	 * redirects back to evalResults
	 */
	@GetMapping("/doDelete/{id}")
	public String deleteEvaluation(@PathVariable int id, HttpSession session) {
		// deletes the given evaluation
		da.deleteEvaluation(id);
		// refreshes the sessionAttribute for evaluations because one was deleted
		session.setAttribute("evaluations", da.getEvaluations());
		// redirects to eval results so the url does not look weird
		return "redirect:/evalResults";
	}


	/* performed when the user tries to access things that are not
	within their privileges
	 */
	@GetMapping("/permissionDenied")
	public String errorMessage() {
		return "error/permissionDenied";
	}


	// returns the body of Course object in json format
	@GetMapping(value = "/course/{code}", produces = "application/json")
	@ResponseBody
	public Course getContainer(@PathVariable String code) {
		return da.getCourse(code);
	}



}

	/* Loads the index page
	 */
