package com.tu.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRequest {

    @NotBlank(message = "name can't be null or blank")
    String name;

    @NotBlank(message = "address can't be null or blank")
    String address;

    @NotBlank(message = "phoneNumber can't be null or blank")
    @Pattern(regexp = "^\\d{10}$", message = "phoneNumber can only contain 10 digits")
    String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Past(message = "dateOfBirth can't be in the future")
    LocalDate dateOfBirth;

    @NotBlank(message = "title can't be null or blank")
    String title;

    @NotBlank(message = "sin can't be null or blank")
    @Pattern(regexp = "^\\d{9}$", message = "SIN can only contain 9 digits")
    String sin;

}
