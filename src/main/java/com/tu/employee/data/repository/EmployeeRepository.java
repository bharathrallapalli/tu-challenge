package com.tu.employee.data.repository;

import com.tu.employee.data.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select e from employee e where e.phoneNumber = ?1 and (e.name = ?2 or e.sin = ?3)")
    List<Employee> findByPhoneNumberAndNameOrSin(@Param("phoneNumber") String phoneNumber,
                                                 @Param("name") String name,
                                                 @Param("sin") String sin);

    @Query(nativeQuery = true, value = "SELECT * from employee where date_part('year',age(dob)) >" +
            " :age and title like %:title%")
    List<Employee> findByAgeAndTitle(@Param("title") String title, @Param("age") int age);
}
