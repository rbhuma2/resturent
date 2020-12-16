package com.core.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    public HttpEntity<EntityModel<User>> saveUserData(@Valid @RequestBody User user) {
    	User savedUserData = userService.saveUserData(user);

        EntityModel<User> userResponse = EntityModel.of(new User());
        userResponse.add(
                linkTo(methodOn(UserController.class).findUser(savedUserData.getIdentifier())).withRel("user").expand());
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedUserData.getIdentifier()).toUri());

        return new ResponseEntity<>(userResponse, httpHeaders, HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{id}")
    public HttpEntity<EntityModel<User>> findUser(@PathVariable("id") String id) {
    	User giftCard = userService.findUserData(id);

        if (giftCard == null) {
            return new ResponseEntity<EntityModel<User>>(HttpStatus.OK);
        }

        EntityModel<User> giftCardResponse = EntityModel.of(giftCard);

        
        giftCardResponse.add(linkTo(methodOn(UserController.class).findUser(id)).withSelfRel().expand());

        
        return ResponseEntity.ok(giftCardResponse);
    }
    
    
}
