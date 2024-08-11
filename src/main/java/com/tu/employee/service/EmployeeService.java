package com.tu.employee.service;

import com.tu.employee.data.entity.Employee;
import com.tu.employee.data.repository.EmployeeRepository;
import com.tu.employee.model.CreateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> findEmployeesByAgeAndTitle(String title, int age, boolean ignoreCase) {
        if(!ignoreCase) {
            return employeeRepository.findByAgeAndTitle(title, age);
        }
        return employeeRepository.findByAgeAndTitleIgnoreCase(title, age);
    }

    public Employee createEmployee(CreateRequest request) {
        validateRequest(request);
        var employee = Employee.builder()
                .name(request.getName())
                .address(request.getAddress())
                .dateOfBirth(request.getDateOfBirth())
                .phoneNumber(request.getPhoneNumber())
                .title(request.getTitle())
                .sin(getLast4Digits(request.getSin())).build();

        var createdEmployee = employeeRepository.save(employee);
        log.info("Created employee with id: {}", createdEmployee.getEmployeeId());
        return createdEmployee;
    }

    public Page<Employee> getEmployeesByPage(PageRequest pageRequest) {
        return employeeRepository.findAll(pageRequest);
    }

    public void deleteEmployee(long id) {
        var employee = employeeRepository.findById(id);
        employeeRepository.delete(employee.orElseThrow(() -> new IllegalArgumentException("id not found")));
    }

    public void deleteAll() {
        employeeRepository.deleteAll();
    }

    public void deleteSome(int size) {
        var employees = getEmployeesByPage(PageRequest.of(1, size));
        employeeRepository.deleteAll(employees);
    }

    private void validateRequest(CreateRequest request) {
        validateDOB(request);
        validateDuplicate(request);
    }

    private void validateDuplicate(CreateRequest request) {
        var existing = employeeRepository.findByPhoneNumberAndNameOrSin
                (request.getPhoneNumber(), request.getName(), getLast4Digits(request.getSin()));
        if (!existing.isEmpty()) {
            throw new IllegalArgumentException("Duplicate Request");
        }
    }

    private void validateDOB(CreateRequest request) {
        if (!request.getDateOfBirth().isBefore(LocalDate.now().minusYears(18))) {
            throw new IllegalArgumentException("Age should be at least 18");
        } else if(!request.getDateOfBirth().isAfter(LocalDate.now().minusYears(100))) {
            throw new IllegalArgumentException("Age should not be more than 100");
        }
    }

    private String getLast4Digits(String input) {
        return input.substring(input.length() - 4);
    }
}
