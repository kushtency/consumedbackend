package com.nagarro.consumedbackend.dto.backendResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Message {
    private int code;
    private String message;
    private String timestamp;

}
