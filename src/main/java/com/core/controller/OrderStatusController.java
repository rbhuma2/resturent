package com.core.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.core.model.CartDataResponse;
import com.core.model.Paging;
import com.core.service.OrderStatusService;

@RestController
@RequestMapping(value = "/v1/orderStauts")
public class OrderStatusController {
	
	
	private static final int INITIAL_PAGE = 1;
    private static final int INITIAL_PAGE_SIZE = 10;
    
	@Autowired
	private OrderStatusService orderStatusService;
	
	@GetMapping
	public  HttpEntity<CollectionModel<EntityModel<CartDataResponse>>> findCartDataResponses(
			@RequestHeader HttpHeaders httpHeaders,
			@RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {
    	
		int evalPageSize = size == null || size < 1 ? INITIAL_PAGE_SIZE : size;
        int evalPage = page == null || page < 1 ? INITIAL_PAGE : page;
		
		List<String> emailList = httpHeaders.getValuesAsList("X-User-Id");
    	List<Link> links = new ArrayList<>();
    	List<EntityModel<CartDataResponse>> cartDataResponse = new ArrayList<>();
    	
    	Link self = linkTo(methodOn(OrderStatusController.class).findCartDataResponses(httpHeaders, evalPage, evalPageSize))
                .withSelfRel().expand();
        links.add(self);
    	if(emailList.isEmpty()) {
    		return ResponseEntity.ok(CollectionModel.of(cartDataResponse, links));
    	}
    	Paging paging = new Paging();
    	List<CartDataResponse> cartData = orderStatusService.findCardDataResponse(paging, evalPage, evalPageSize, emailList.get(0));

        if (!cartData.isEmpty()) {
        	for(CartDataResponse response : cartData) {
        		Link orderDetailLink = linkTo(methodOn(OrderStatusController.class).findOrderDetails(response.getIdentifier())).withRel("orderDetail")
                        .expand();
        		cartDataResponse.add(EntityModel.of(response, orderDetailLink));
        	}
        	
        	
        	if (paging.getFirstPage() > 0) {
                links.add(linkTo(methodOn(OrderStatusController.class).findCartDataResponses(httpHeaders, paging.getFirstPage(),
                        paging.getPageSize())).withRel("first").expand());
            }
            if (paging.getLastPage() > 0) {
                links.add(linkTo(methodOn(OrderStatusController.class).findCartDataResponses(httpHeaders, paging.getLastPage(),
                        paging.getPageSize())).withRel("last").expand());
            }
            if (paging.getNextPage() > 0) {
                links.add(linkTo(methodOn(OrderStatusController.class).findCartDataResponses(httpHeaders, paging.getNextPage(),
                        paging.getPageSize())).withRel("next").expand());
            }
            if (paging.getPreviousPage() > 0) {
                links.add(linkTo(methodOn(OrderStatusController.class).findCartDataResponses(httpHeaders, paging.getPreviousPage(),
                        paging.getPageSize())).withRel("previous").expand());
            }
            if (paging.getCurrentPage() > 0) {
                links.add(linkTo(methodOn(OrderStatusController.class).findCartDataResponses(httpHeaders, paging.getCurrentPage(),
                        paging.getPageSize())).withRel("current").expand());
            }
        }

               
        
        
        return ResponseEntity.ok(CollectionModel.of(cartDataResponse, links));
    }
	
	@GetMapping(value = "/{id}")
	public HttpEntity<EntityModel<CartDataResponse>> findOrderDetails(@PathVariable("id") String id) {
		CartDataResponse orderDetails = orderStatusService.findOrderDetails(id);

        if (orderDetails == null) {
            return new ResponseEntity<EntityModel<CartDataResponse>>(HttpStatus.OK);
        }

        EntityModel<CartDataResponse> orderDetailResponse = EntityModel.of(orderDetails);

        orderDetailResponse.add(linkTo(methodOn(OrderStatusController.class).findOrderDetails(id)).withSelfRel().expand());

        orderDetailResponse
                .add(linkTo(methodOn(OrderStatusController.class).findCartDataResponses(null, null, null)).withRel("up").expand());

        return ResponseEntity.ok(orderDetailResponse);
    }

}
