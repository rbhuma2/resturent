package com.core.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.core.mongo.data.entity.User;
import com.core.service.UserService;
import com.core.validator.UserValidator;

@RestController
@RequestMapping(value = "/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(userValidator);
    }
    
        
    @PostMapping
    public HttpEntity<EntityModel<User>> saveUserData(@RequestHeader HttpHeaders httpHeaders, 
    		@Valid @RequestBody User user) {
    	
    	List<String> emailList = httpHeaders.getValuesAsList("X-User-Id");
    	
    	User savedUserData = userService.saveUserData(user, emailList);

        EntityModel<User> userResponse = EntityModel.of(new User());
        userResponse.add(
                linkTo(methodOn(UserController.class).findUser(savedUserData.getIdentifier())).withRel("user").expand());
        
        return new ResponseEntity<>(userResponse, httpHeaders, HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{id}")
    public HttpEntity<EntityModel<User>> findUser(@PathVariable("id") String id) {
    	User user = userService.findUserData(id);

        if (user == null) {
            return new ResponseEntity<EntityModel<User>>(HttpStatus.OK);
        }

        EntityModel<User> userResponse = EntityModel.of(user);

        
        userResponse.add(linkTo(methodOn(UserController.class).findUser(id)).withSelfRel().expand());

        
        return ResponseEntity.ok(userResponse);
    }
    
    
}
