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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.model.CartDataResponse;
import com.core.service.OrderStatusService;

@RestController
@RequestMapping(value = "/v1/orderStauts")
public class OrderStatusController {
	
	@Autowired
	private OrderStatusService orderStatusService;
	
	@GetMapping
	public HttpEntity<EntityModel<CartDataResponse>> findCartData(@RequestHeader HttpHeaders httpHeaders) {
    	
    	List<String> emailList = httpHeaders.getValuesAsList("X-User-Id");
    	if(emailList.isEmpty()) {
    		return new ResponseEntity<EntityModel<CartDataResponse>>(HttpStatus.OK);
    	}
    	CartDataResponse cartData = orderStatusService.findCardDataResponse();

        if (cartData == null) {
            return new ResponseEntity<EntityModel<CartDataResponse>>(HttpStatus.OK);
        }

        EntityModel<CartDataResponse> cartDataResponse = EntityModel.of(cartData);

        
        cartDataResponse.add(linkTo(methodOn(OrderStatusController.class).findCartData(httpHeaders)).withSelfRel().expand());

        return new ResponseEntity<>(cartDataResponse, httpHeaders, HttpStatus.OK);
    }

}
