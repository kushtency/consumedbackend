package com.nagarro.consumedbackend.dto.consumeApiResponse;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class NationalityResponse {
    private int count;
    private String name;
    private List<CountryInfo> country;

    @Data
    @NoArgsConstructor
    public static class CountryInfo {
        @JsonProperty("country_id")
        private String countryId;
        private double probability;
    }

}
