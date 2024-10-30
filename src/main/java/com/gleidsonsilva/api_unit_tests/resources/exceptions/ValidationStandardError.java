package com.gleidsonsilva.api_unit_tests.resources.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ValidationStandardError {
    private LocalDateTime timestamp;
    private Integer status;
    private List<String> errorsList;
    private String path;
}
