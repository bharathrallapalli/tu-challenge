package com.tu.employee.controller;

import com.tu.employee.data.entity.Employee;
import com.tu.employee.model.CreateRequest;
import com.tu.employee.model.SearchRequest;
import com.tu.employee.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.UUID;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;


    @PutMapping
    ResponseEntity<Employee> createEmployee(@Valid @RequestBody CreateRequest request) {
        var employee =  employeeService.createEmployee(request);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/search")
    List<Employee> findAll(@RequestBody @Valid SearchRequest searchRequest) {
        return employeeService.findEmployeesByAgeAndTitle(searchRequest.getTitle(), searchRequest.getAge());
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") long id) {
        employeeService.deleteEmployee(id);
    }

    @DeleteMapping("/all")
    void delete() {
        employeeService.deleteAll();
    }
}
