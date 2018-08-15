package com.example.test.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.test.model.Patient;
import com.example.test.model.User;
import com.example.test.repository.PatientRepository;
import com.example.test.service.PatientService;
import com.example.test.service.SecurityService;
import com.example.test.service.UserService;
import com.example.test.validator.PatientValidator;
import com.example.test.validator.UserValidator;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;

	@Autowired
	private PatientValidator patientValidator;

	@Autowired
	PatientRepository patientRepo;

	private PatientService patientService;

	@Autowired
	public void setPatientService(PatientService patientService) {
		this.patientService = patientService;
	}

	// @RequestMapping(value = "/login", method = RequestMethod.GET)
	// public String login(Model model, String error, String logout) {
	// if (error != null)
	// model.addAttribute("error", "Your username and password is invalid.");
	//
	// if (logout != null)
	// model.addAttribute("message", "You have been logged out successfully.");
	//
	// return "login";
	// }
	// ----------------------------JSP-----------------------------
	@RequestMapping(value = "/adminPage", method = RequestMethod.GET)
	public String admin(Model model) {
		return "adminPage";
	}

	@RequestMapping(value = { "/userPage" }, method = RequestMethod.GET)
	public ModelAndView userPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("userPage");
		return model;
	}

	// --------------------Thymleaf view----------------------------
	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	public String welcomePage(Model model) {
		// model.addAttribute("title","WEB");
		return "welcomePage";
	}

	// Login page
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {

		return "loginPage";
	}

	// Registration page get
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}

	// Registration page post
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		userValidator.validate(user, bindingResult);

		ModelAndView modelAndView = new ModelAndView();
		User userExists = userService.findByUsername(user.getUsername());
		System.out.println("in ra:" + userExists);
		if (userExists != null) {
			bindingResult.rejectValue("username", "error.user",
					"There is already a user registered with the email provided");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			userService.save(user);
			modelAndView.addObject("successMessage", "User has been registered successfully");
			modelAndView.addObject("user", new User());
			modelAndView.setViewName("redirect:/welcome");

			securityService.autologin(user.getUsername(), user.getPasswordConfirm());

		}
		return modelAndView;

	}

	// Method Save
	@RequestMapping(value = { "/patient/save" }, method = RequestMethod.POST)
	public String process(@Valid Patient patient, BindingResult result, RedirectAttributes redirect) {
		patientValidator.validate(patient, result);

		if (result.hasErrors()) {
			return "addPatient";
		}
		patientService.save(patient);

		redirect.addFlashAttribute("success", "Save patient succcesful");

		return "redirect:/listPatient";

	}

	// Add Page
	@RequestMapping(value = "/addPatient", method = RequestMethod.GET)
	public ModelAndView addPat() {
		ModelAndView modelAndView = new ModelAndView();
		Patient patient = new Patient();
		// modelAndView.addObject("errorMessage", "create patient error");
		modelAndView.addObject("patient", patient);
		modelAndView.setViewName("addPatient");
		return modelAndView;
	}

	// Show List Patient Page
	@RequestMapping(value = "/listPatient", method = RequestMethod.GET)
	// public ModelAndView listPat() {
	// ModelAndView modelAndView = new ModelAndView();
	//// Patient patient1 = new Patient();
	// Iterable<Patient> listPatients = patientService.listAllPatient();
	// modelAndView.addObject("listPatients", listPatients);
	//// modelAndView.addObject("patient1", patient1);
	// modelAndView.setViewName("listPatient");
	// return modelAndView;
	// }
	public String list(Model model, HttpServletRequest request, RedirectAttributes attr) {
		request.getSession().setAttribute("listPatients", null);
		if (model.asMap().get("success") != null)
			attr.addFlashAttribute("success", model.asMap().get("success").toString());
		return "redirect:/patient/page/1";

		// model.addAttribute("listPatients", patientService.listAllPatient());
		// return "listPatient";
	}

	// Show PageNumber
	@RequestMapping(value = "/patient/page/{pageNumber}", method = RequestMethod.GET)
	public String showPatientPage(Model model, @PathVariable int pageNumber, HttpServletRequest request) {
		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("listPatients");
		int pagesize = 5;
		List<Patient> list = (List<Patient>) patientService.listAllPatient();
		System.out.println(list.size());
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int gotoPage = pageNumber - 1;
			if (gotoPage <= pages.getPageCount() && gotoPage >= 0) {
				pages.setPage(gotoPage);
			}
		}
		request.getSession().setAttribute("listPatients", pages);

		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();

		String baseUrl = "/patient/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("patients", pages);

		return "listPatient";

	}

	// Show admin page
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public ModelAndView home() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getUsername());
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("adminPage");
		return modelAndView;
	}

	// Show User Info page
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public ModelAndView userInfo() {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = userService.findByUsername(auth.getName());
		modelAndView.addObject("userName", "Welcome " + user.getUsername());
		modelAndView.addObject("adminMessage", "Content Available Only for Users with Admin Role");
		modelAndView.setViewName("userInfoPage");
		return modelAndView;
	}

	// Find Patient List
	@RequestMapping(value = "/patient/search/{pageNumber}", method = RequestMethod.GET)
	public String searchPatient(@RequestParam(value = "s", required = false) String s, Model model,
			HttpServletRequest request, @PathVariable int pageNumber) {
		if (s.equals("")) {
			return "redirect:/listPatient";
		}

		List<Patient> list = patientService.search(s);
		if (list == null) {
			return "redirect:/listPatient";
		}

		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("listPatients");
		int pagesize = 5;

		pages = new PagedListHolder<>(list);
		pages.setPageSize(pagesize);

		final int gotoPage = pageNumber - 1;
		if (gotoPage <= pages.getPageCount() && gotoPage >= 0) {
			pages.setPage(gotoPage);
		}

		request.getSession().setAttribute("listPatients", pages);

		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();

		String baseUrl = "/patient/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("patients", pages);

		return "listPatient";
		// Patient patient1 = new Patient();
		// model.addAttribute("patients", patientService.search(s));
		// model.addAttribute("patient1", patient1);
		// return "listPatient";
	}

	// Edit Patient
	@RequestMapping(value = "/patient/{id}/edit", method = RequestMethod.GET)
	public String edit(@PathVariable int id, Model model) {
		model.addAttribute("patient", patientService.findOne(id));
		return "addPatient";
	}

	// Delete Patient

	@RequestMapping(value = "/patient/{id}/delete", method = RequestMethod.GET)
	public String delete(@PathVariable int id, RedirectAttributes redirect) {
		patientService.delete(id);
		redirect.addFlashAttribute("success", "Deleted employee successfully!");
		return "redirect:/listPatient";
	}

	@RequestMapping(value = { "/sample" }, method = RequestMethod.GET)
	public String testJspView() {

		return "sample";
	}

	// @RequestMapping(value = "/403", method = RequestMethod.GET)
	// public String accessDenied(Model model, Principal principal) {
	//
	// if (principal != null) {
	// User loginedUser = (User) ((Authentication) principal).getPrincipal();
	//
	// String userInfo = WebUtils.toString(loginedUser);
	//
	// model.addAttribute("userInfo", userInfo);
	//
	// String message = "Hi " + principal.getName() //
	// + "<br> You do not have permission to access this page!";
	// model.addAttribute("message", message);
	//
	// }
	//
	// return "403Page";
	// }

}
