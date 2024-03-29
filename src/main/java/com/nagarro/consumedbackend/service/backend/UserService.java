package com.nagarro.consumedbackend.service.backend;

import com.nagarro.consumedbackend.Constants.SortOrder;
import com.nagarro.consumedbackend.Constants.SortType;
import com.nagarro.consumedbackend.dto.backendResponse.PaginationResponse;
import com.nagarro.consumedbackend.model.User;
import com.nagarro.consumedbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public PaginationResponse getUsers(String sortType, String sortOrder, int limit, int offset) {
        Page<User> users = userRepository
                .findAll(
                        PageRequest.of(offset, limit)
                                .withSort(Sort.by(Sort.Direction.ASC,sortType))
                );

        List<User> usersList = new ArrayList<>();
        if(sortOrder.equalsIgnoreCase(SortOrder.EVEN.name()) && sortType.equalsIgnoreCase(SortType.AGE.name())){
            usersList = users.stream().filter(user -> user.getAge()%2 == 0).collect(Collectors.toList());
            usersList.addAll(users.stream().filter(user -> user.getAge()%2 != 0).toList());
        }else if(sortOrder.equalsIgnoreCase(SortOrder.ODD.name()) && sortType.equalsIgnoreCase(SortType.AGE.name())){
            usersList = users.stream().filter(user -> user.getAge()%2 != 0).collect(Collectors.toList());
            usersList.addAll(users.stream().filter(user -> user.getAge()%2 == 0).toList());
        }else if(sortOrder.equalsIgnoreCase(SortOrder.EVEN.name()) && sortType.equalsIgnoreCase(SortType.NAME.name())) {
            usersList = users.stream().filter(user -> user.getName().toCharArray().length%2 == 0).collect(Collectors.toList());
            usersList.addAll(users.stream().filter(user -> user.getName().toCharArray().length%2 != 0).toList());
        }else if(sortOrder.equalsIgnoreCase(SortOrder.ODD.name()) && sortType.equalsIgnoreCase(SortType.NAME.name())) {
            usersList = users.stream().filter(user -> user.getName().toCharArray().length%2 != 0).collect(Collectors.toList());
            usersList.addAll(users.stream().filter(user -> user.getName().toCharArray().length%2 == 0).toList());
        }

        PaginationResponse response  = new PaginationResponse();
        PaginationResponse.PageInfo pageInfo  = new PaginationResponse.PageInfo();

        pageInfo.setHasNextPage(users.hasNext());
        pageInfo.setHasPreviousPage(users.hasPrevious());
        pageInfo.setTotal(users.getSize());
        response.setData(usersList);
        response.setPageInfo(pageInfo);
        return response;
    }

}
