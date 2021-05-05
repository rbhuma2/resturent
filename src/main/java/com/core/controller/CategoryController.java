package com.core.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.core.mongo.data.entity.MenuItemData;

@RestController
@RequestMapping(value = "/v1/category")
public class CategoryController {
	
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	@GetMapping
	public HttpEntity<CollectionModel<EntityModel<Object>>> findCatefories(
			@RequestParam(value = "item", required = false) String item) {
		
		List<EntityModel<Object>> categoryResource = new ArrayList<>();
		List<Link> links = new ArrayList<>();
		if(item == null) {
			item = "Menu";
		}
		//Query query = new Query().with(new Sort(Sort.Direction.DESC, "category"));
		//List<Object> categorysList = mongoTemplate.query(MenuItemData.class).distinct("category").all();
		//List<Object> categorysLists = mongoTemplate.findDistinct(query, "category", MenuItemData.class, Object.class);
		Criteria criteria = new Criteria("item").is(item);
		Query query = new Query();
		query.addCriteria(criteria);
		List<Object> categorysList =  mongoTemplate.findDistinct(query,"category",MenuItemData.class,Object.class);
		Collections.sort(categorysList, Collections.reverseOrder()); 
		for(Object object : categorysList) {
			categoryResource.add(EntityModel.of(object));
		}
		
		Link self = linkTo(methodOn(CategoryController.class).findCatefories(item))
                .withSelfRel().expand();
        links.add(self);
		
		return ResponseEntity.ok(CollectionModel.of(categoryResource, links));
		
	}

}
