package com.tu.employee.controller;

import com.tu.employee.data.entity.Employee;
import com.tu.employee.model.CreateRequest;
import com.tu.employee.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
        var employee = employeeService.createEmployee(request);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    List<Employee> findAll() {
        return employeeService.findAll();
    }

    @GetMapping("/paginated")
    Page<Employee> findAll(@RequestParam(value = "offset", required = false) Integer offset,
                           @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        if(null == offset) offset = 0;
        if(null == pageSize) pageSize = 10;

        return employeeService.getEmployeesByPage(PageRequest.of(offset, pageSize, Sort.by("employeeId")));
    }

    @GetMapping("/search")
    List<Employee> findAll(
            @Valid
            @NotBlank(message = "title can't be null or blank")
            @RequestParam("title") String title,

            @Valid
            @NotNull(message = "age can't be null or blank")
            @Min(value = 18, message = "age should be in between 18 and 100")
            @Max(value = 100, message = "age should be in between 18 and 100")
            @RequestParam("age") int age,

            @RequestParam(value = "ignoreCase", required = false) boolean ignoreCase
            ) {
        return employeeService.findEmployeesByAgeAndTitle(title, age, ignoreCase);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable("id") long id) {
        employeeService.deleteEmployee(id);
    }

    @DeleteMapping("/all")
    void delete() {
        employeeService.deleteAll();
    }

    @DeleteMapping("/some")
    void deleteSome() {
        employeeService.deleteSome(1);
    }
}
