package com.tu.employee.data.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity(name = "employee")
@Table(name = "employee")
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Employee {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "id_gen")
    @TableGenerator(name = "id_gen", table = "ID_GEN", pkColumnName = "gen_name", valueColumnName = "gen_val",
            pkColumnValue = "id_gen", initialValue = 10000, allocationSize = 100)
    Long employeeId;

    @Column(name = "name")
    String name;

    @Column(name = "address")
    String address;

    @Column(name = "dob")
    LocalDate dateOfBirth;

    @Column(name = "phonenumber")
    String phoneNumber;

    @Column(name = "title")
    String title;

    @Column(name = "sin")
    String sin;
}
