package com.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.exception.ResourceNotFoundException;
import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping("/employees")
	public ResponseEntity<?> getAllEmployee() {
		
		try {
			
			List<Employee> employees = employeeRepository.findAll();
			return ResponseEntity.ok(employees);
			
		}catch(Exception e) {
			
			e.printStackTrace();
			return (ResponseEntity<?>) ResponseEntity.notFound();
			
		}
		
	}
	
	@PostMapping("/employee")
	public ResponseEntity<?> createEmployee(@RequestBody Employee employee) {
		
		try {
			
			Employee item = employeeRepository.save(employee);
			return ResponseEntity.ok(item);
			
		} catch(Exception e) {
			
			e.printStackTrace();
			return (ResponseEntity<?>) ResponseEntity.internalServerError();
			
		}
	}
	
	@GetMapping("/employee")
	public ResponseEntity<?> getEmployeeById(@RequestParam("id") Long id) {
		
		try {
			
			Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Employee id not found"));
			return ResponseEntity.ok(employee);
		
		} catch(Exception e) {
			
			e.printStackTrace();
			return (ResponseEntity<?>) ResponseEntity.badRequest();
			
		}
	}
	
	@PutMapping("/employee")
	public ResponseEntity<?> updateEmployee(@RequestParam("id") Long id,
			                                @RequestBody Employee item) {
		
		try {
			
			Employee employee = employeeRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Employee id not found"));
			
			employee.setFirstName(item.getFirstName());
			employee.setLastName(item.getLastName());
			employee.setEmailId(item.getEmailId());
			
			employeeRepository.save(employee);
			
			return ResponseEntity.ok(employee);
			
		} catch(Exception e) {
			e.printStackTrace();
			return (ResponseEntity<?>) ResponseEntity.badRequest();
		}
	}
	
	@DeleteMapping("/employee")
	public ResponseEntity<?> deleteEmployee(@RequestParam("id") Long id) {
		
		try {
	
			employeeRepository.deleteById(id);
			
			return (ResponseEntity<?>) ResponseEntity.noContent();
			
		} catch(Exception e) {
			e.printStackTrace();
			return (ResponseEntity<?>) ResponseEntity.badRequest();
		}

	}
	
	
}
