package com.core.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.core.exception.application.InvalidDataException;
import com.core.model.CartDataResponse;
import com.core.mongo.data.entity.CartData;
import com.core.service.CartDataService;
import com.core.validator.CartDataValidator;

@RestController
@RequestMapping(value = "/v1/cart")
public class CartDataController {

    @Autowired
    private CartDataService cartDataService;

    @Autowired
    private CartDataValidator cartDataValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(cartDataValidator);
    }
       
    @GetMapping
    public HttpEntity<EntityModel<CartDataResponse>> findCartData(@RequestHeader HttpHeaders httpHeaders) {
    	
    	List<String> emailList = httpHeaders.getValuesAsList("X-User-Id");
    	if(emailList.isEmpty()) {
    		return new ResponseEntity<EntityModel<CartDataResponse>>(HttpStatus.OK);
    	}
    	CartDataResponse cartData = cartDataService.findCardDataResponse(emailList.get(0));

        if (cartData == null) {
            return new ResponseEntity<EntityModel<CartDataResponse>>(HttpStatus.OK);
        }

        EntityModel<CartDataResponse> cartDataResponse = EntityModel.of(cartData);

        
        cartDataResponse.add(linkTo(methodOn(CartDataController.class).findCartData(httpHeaders)).withSelfRel().expand());

        return new ResponseEntity<>(cartDataResponse, httpHeaders, HttpStatus.OK);
    }
    
    @PatchMapping(value = "/{id}")
    public HttpEntity<EntityModel<CartData>> saveCartData(@RequestHeader HttpHeaders httpHeaders, @PathVariable("id") String id,
    		@RequestBody CartData cartData) {

    	List<String> emailList = httpHeaders.getValuesAsList("X-User-Id");
    	if(emailList.isEmpty()) {
    		throw new InvalidDataException("author.invalid");
    	}
    	cartDataService.saveCardData(emailList.get(0), id, cartData);
        EntityModel<CartData> savedItemData = EntityModel.of(new CartData());
        savedItemData.add(linkTo(methodOn(CartDataController.class).findCartData(httpHeaders)).withRel("cardData").expand());
        return ResponseEntity.ok(savedItemData);
    }
    
    
}
