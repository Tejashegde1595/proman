package org.example.proman.api.Controllers;

import org.example.proman.api.model.CreateUserRequest;
import org.example.proman.api.model.CreateUserResponse;
import org.example.proman.api.model.UserDetailsResponse;
import org.example.proman.api.model.UserStatusType;
import org.example.proman.service.Business.UserBusinesService;
import org.example.proman.service.Entity.UserEntity;
import org.example.proman.service.exception.ResourceNotFoundException;
import org.example.proman.service.exception.UnauthorizedException;
import org.example.proman.service.type.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.UUID;
@RestController
@RequestMapping("/")
public class UserAdminController {

    @Autowired
    private UserBusinesService userAdminBusinessService;

    @RequestMapping(method = RequestMethod.GET, path = "/users/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getUser(@PathVariable("id") final String userUuid,@RequestHeader("authorization") final String authorization) throws ResourceNotFoundException, UnauthorizedException {
            final UserEntity userEntity = userAdminBusinessService.getUser(userUuid,authorization);
            UserDetailsResponse userDetailsResponse = new UserDetailsResponse().id(userEntity.getUuid()).firstName(userEntity.getFirstName())
                    .lastName(userEntity.getLastName()).emailAddress(userEntity.getEmail())
                    .mobileNumber(userEntity.getMobilePhone()).status(UserStatusType.valueOf(UserStatus.getEnum(userEntity.getStatus()).name()));
            return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST,path="/users",produces = MediaType.APPLICATION_JSON_UTF8_VALUE,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CreateUserResponse> getUser(final CreateUserRequest createUserRequest){
        UserEntity userEntity = new UserEntity();
        userEntity.setUuid(UUID.randomUUID().toString());
        userEntity.setFirstName(createUserRequest.getFirstName());
        userEntity.setLastName(createUserRequest.getLastName());
        userEntity.setEmail(createUserRequest.getEmailAddress());
        userEntity.setMobilePhone(createUserRequest.getMobileNumber());
        userEntity.setStatus(UserStatus.ACTIVE.getCode());
        userEntity.setCreatedAt(ZonedDateTime.now());
        userEntity.setCreatedBy("api-backend");

        final UserEntity createdUser = userAdminBusinessService.createUser(userEntity);

        final CreateUserResponse userResponse = new CreateUserResponse().id(createdUser.getUuid()).status(UserStatusType.ACTIVE);

        return new ResponseEntity<CreateUserResponse>(userResponse, HttpStatus.CREATED);
    }
}
