package com.luv2code.springboot.thymeleafdemo.controller;

import com.luv2code.springboot.thymeleafdemo.entity.Employee;
import com.luv2code.springboot.thymeleafdemo.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	//	add an initbinder ... to convert trim input strings
	//	remove leading and trailing whitespace
	//	resolve issue for our validation
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
		StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
		dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
	}

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}
	// add mapping for "/list"

	@GetMapping("/list")
	public String listEmployees(Model theModel) {

		List<Employee> theEmployees = employeeService.findAll();

		// add to the spring model
		theModel.addAttribute("employees", theEmployees);

		return "employees/list-employees";
	}

	@GetMapping("/showFormForAdd")
	public String showFormForAdd(Model theModel) {

		//	create model attribute to bind form data
		Employee theEmployee = new Employee();
		theModel.addAttribute("employee", theEmployee);

		return "employees/employee-form";
	}

	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("employeeId") int theId, Model theModel) {
		//	get the employee from the service
		Employee theEmployee = employeeService.findById(theId);

		//	set employee in the model to prepopulate the form
		theModel.addAttribute("employee", theEmployee);

		//	send over to out form
		return "employees/employee-form";
	}

	@PostMapping("/save")
	public String saveEmployee(@Valid @ModelAttribute("employee") Employee theEmployee,
							   BindingResult theBindingResult) {
		System.out.println("Last name: |"+theEmployee.getLastName()+"|");
		if (theBindingResult.hasErrors()) {
			return "employees/employee-form";
		} else {
			// save
			employeeService.save(theEmployee);
			return "redirect:/employees/list";
		}
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("employeeId") int theId) {

		// delete
		employeeService.deleteById(theId);

		//	use a redirect to prevent duplicated submissions
		return "redirect:/employees/list";
	}
}