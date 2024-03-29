package com.nagarro.consumedbackend.controller.user;

import com.nagarro.consumedbackend.dto.backendRequest.Size;
import com.nagarro.consumedbackend.dto.backendResponse.Message;
import com.nagarro.consumedbackend.factory.ValidatorFactory;
import com.nagarro.consumedbackend.model.User;
import com.nagarro.consumedbackend.service.backend.UserService;
import com.nagarro.consumedbackend.service.consumeAPI.FetchAndSaveDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/users")
public class UserController {
    private final FetchAndSaveDetailsService fetchAndSaveDetailsService;
    private final UserService userService;

    @Autowired
    public UserController(
            FetchAndSaveDetailsService fetchAndSaveDetailsService,
            UserService userService
    ){
        this.fetchAndSaveDetailsService = fetchAndSaveDetailsService;
        this.userService = userService;
    }
    @PostMapping("")
    public Flux<User> fetchUserDetails(@RequestBody Size size) {
        var isLimitValid = ValidatorFactory.getValidator("numeric").validate(String.valueOf(size.getSize()));
        if(isLimitValid) {
            return fetchAndSaveDetailsService.fetchResult(size.getSize());
        }else{
            return null;
        }
    }

    @GetMapping("")
    public ResponseEntity<Object> getUserDetails(
            @RequestParam(name = "sortType") String sortType,
            @RequestParam(name = "sortOrder") String sortOrder,
            @RequestParam(name = "limit") int limit,
            @RequestParam(name = "offset") int offset
    ){
        var limitIsValid = ValidatorFactory.getValidator("numeric").validate(String.valueOf(limit));
        var offsetIsValid = ValidatorFactory.getValidator("numeric").validate(String.valueOf(offset));
        var sortTypeIsValid = ValidatorFactory.getValidator("name").validate(sortType);
        var sortOrderIsValid = ValidatorFactory.getValidator("name").validate(sortOrder);

        if(limitIsValid && offsetIsValid && sortOrderIsValid && sortTypeIsValid) {
            var users = userService.getUsers(sortType, sortOrder, limit, offset);
            return ResponseEntity.ok(users);
        }else{
            return ResponseEntity.status(400).body(
                    Message.builder()
                            .code(400)
                            .message("bad request, check request parameter")
                            .timestamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .build()
            );
        }
    }
}
