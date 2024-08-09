package com.tu.employee.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorResponse {
    String code;
    String value;
}
