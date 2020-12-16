package com.core.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.core.mongo.data.entity.MenuItemData;

@RestController
@RequestMapping(value = "/v1/category")
public class CategoryController {
	
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	@GetMapping
	public HttpEntity<CollectionModel<EntityModel<Object>>> findCatefories() {
		
		List<EntityModel<Object>> categoryResource = new ArrayList<>();
		List<Link> links = new ArrayList<>();
		List<Object> categorysList = mongoTemplate.query(MenuItemData.class).distinct("category").all();
		
		for(Object object : categorysList) {
			categoryResource.add(EntityModel.of(object));
		}
		
		Link self = linkTo(methodOn(CategoryController.class).findCatefories())
                .withSelfRel().expand();
        links.add(self);
		
		return ResponseEntity.ok(CollectionModel.of(categoryResource, links));
		
	}

}
