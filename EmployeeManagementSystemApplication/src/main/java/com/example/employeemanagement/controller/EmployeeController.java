package com.example.employeemanagement.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.service.EmployeeService;


@Controller
public class EmployeeController {
	
	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		super();
		this.employeeService = employeeService;
	}
	
	// handler method to handle list employee and return mode and view
	@GetMapping("/employee")
	public String listEmployees(Model model) {
		model.addAttribute("employee", employeeService.getAllEmployees());
		return "employee";
	}
	
	@GetMapping("/employee/new")
	public String createEmployeeForm(Model model) {
		
		// create employee object to hold employee form data
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "create_employee";
		
	}
	
	@PostMapping("/employee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		employeeService.saveEmployee(employee);
		return "redirect:/employee";
	}
	
	@GetMapping("/employee/edit/{id}")
	public String editEmployeeForm(@PathVariable Long id, Model model) {
		model.addAttribute("employee", employeeService.getEmployeeById(id));
		return "edit_employee";
	}

	@PostMapping("/employee/{id}")
	public String updateEmployee(@PathVariable Long id,
			@ModelAttribute("employee") Employee employee,
			Model model) {
		
		// get employee from database by id
		Employee existingEmployee = employeeService.getEmployeeById(id);
		existingEmployee.setId(id);
		existingEmployee.setFirstName(employee.getFirstName());
		existingEmployee.setLastName(employee.getLastName());
		existingEmployee.setEmail(employee.getEmail());
		
		// save updated employee object
		employeeService.updateEmployee(existingEmployee);
		return "redirect:/employee";		
	}
	
	// handler method to handle delete employee request
	
	@GetMapping("/employee/{id}")
	public String deleteEmployee(@PathVariable Long id) {
		employeeService.deleteEmployeeById(id);
		return "redirect:/employee";
	}
	
}
