package com.nagarro.consumedbackend.dto.backendResponse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponse {
    private String timestamp;
    private String message;
    private String code;
}
