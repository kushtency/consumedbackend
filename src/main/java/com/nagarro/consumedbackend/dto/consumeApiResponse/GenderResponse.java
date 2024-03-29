package com.nagarro.consumedbackend.dto.consumeApiResponse;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenderResponse {
    private int count;
    private String name;
    private String gender;
    private double probability;
}
