package com.nagarro.consumedbackend.dto.backendResponse;

import com.nagarro.consumedbackend.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class PaginationResponse {
    private List<User> data;
    private PageInfo pageInfo;

    @AllArgsConstructor
    @Builder
    @Data
    @NoArgsConstructor
    public static class PageInfo {
        private boolean hasNextPage;
        private boolean hasPreviousPage;
        private int total;
    }

}
