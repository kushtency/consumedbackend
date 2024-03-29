package com.nagarro.consumedbackend.dto.consumeApiResponse;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserResponse {
    private List<Result> results;
    private Info info;
    @Data
    @NoArgsConstructor
    public static class Coordinates {
        private String latitude;
        private String longitude;
    }
    @Data
    @NoArgsConstructor
    public static class Timezone {
        private String offset;
        private String description;
    }
    @Data
    @NoArgsConstructor
    public static class Street {
        private int number;
        private String name;
    }
    @Data
    @NoArgsConstructor
    public static class Location {
        private Street street;
        private String city;
        private String state;
        private String country;
        private String postcode;
        private Coordinates coordinates;
        private Timezone timezone;
    }
    @Data
    @NoArgsConstructor
    public static class Name {
        private String title;
        private String first;
        private String last;
    }
    @Data
    @NoArgsConstructor
    public static class Login {
        private String uuid;
        private String username;
        private String password;
        private String salt;
        private String md5;
        private String sha1;
        private String sha256;
    }
    @Data
    @NoArgsConstructor
    public static class Dob {
        private String date;
        private int age;
    }
    @Data
    @NoArgsConstructor
    public static class Registered {
        private String date;
        private int age;
    }
    @Data
    @NoArgsConstructor
    public static class Id {
        private String name;
        private String value;
    }
    @Data
    @NoArgsConstructor
    public static class Picture {
        private String large;
        private String medium;
        private String thumbnail;
    }
    @Data
    @NoArgsConstructor
    public static class Result {
        private String gender;
        private Name name;
        private Location location;
        private String email;
        private Login login;
        private Dob dob;
        private Registered registered;
        private String phone;
        private String cell;
        private Id id;
        private Picture picture;
        private String nat;
    }
    @Data
    @NoArgsConstructor
    public static class Info {
        private String seed;
        private int results;
        private int page;
        private String version;
    }
}
