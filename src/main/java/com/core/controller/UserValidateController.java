package com.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.core.mongo.data.entity.User;
import com.core.service.UserService;

@RestController
@RequestMapping(value = "/v1/userValidate")
public class UserValidateController {

    @Autowired
    private UserService userService;

        
        
    @PostMapping
    public HttpEntity<EntityModel<User>> saveUserData(@RequestBody User user) {
    	User userData = userService.validateUserData(user);
    	
    	User userResponse = new User();
    	userResponse.setAdmin(userData.isAdmin());
    	userResponse.setName(userData.getName());
        
    	EntityModel<User> response = EntityModel.of(userResponse);
        
        HttpHeaders httpHeaders = new HttpHeaders();
        
        return new ResponseEntity<>(response, httpHeaders, HttpStatus.ACCEPTED);
    }
    
    
    
    
}
