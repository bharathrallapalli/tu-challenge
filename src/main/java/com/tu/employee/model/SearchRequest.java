package com.tu.employee.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {

    @NotBlank(message = "title can't be null or blank")
    String title;

    @NotNull(message = "age can't be null or blank")
    @Min(value = 18, message = "age should be in between 18 and 100")
    @Max(value = 65, message = "age should be in between 18 and 100")
    int age;
}
