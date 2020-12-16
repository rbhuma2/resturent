package com.core.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.core.model.MenuListResponse;
import com.core.model.Paging;
import com.core.mongo.data.entity.MenuItemData;
import com.core.service.MenuItemDataService;
import com.core.validator.MenuItemDataValidator;

@RestController
@RequestMapping(value = "/v1/menuItem")
public class MenuItemController {

    private static final int INITIAL_PAGE = 1;
    private static final int INITIAL_PAGE_SIZE = 100;

    @Autowired
    private MenuItemDataService menuItemDataService;

    @Autowired
    private MenuItemDataValidator itemDataValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(itemDataValidator);
    }
    
    @GetMapping
    public HttpEntity<CollectionModel<EntityModel<MenuListResponse>>> findAllItems(
            @RequestParam(value = "filters", required = false) String filters,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        int evalPageSize = size == null || size < 1 ? INITIAL_PAGE_SIZE : size;
        int evalPage = page == null || page < 1 ? INITIAL_PAGE : page;

        List<EntityModel<MenuListResponse>> jobDataResource = new ArrayList<>();
        List<Link> links = new ArrayList<>();

        Paging paging = new Paging();

        List<MenuListResponse> itemDataList = menuItemDataService.findItems(filters, query, evalPage, evalPageSize, paging);
        
        if(itemDataList != null && !itemDataList.isEmpty()) {
            for (MenuListResponse itemData : itemDataList) {
				
				 jobDataResource.add(EntityModel.of(itemData));
				  
			}

            
        }
        

        Link self = linkTo(methodOn(MenuItemController.class).findAllItems(filters, query, evalPage, evalPageSize))
                .withSelfRel().expand();
        links.add(self);

        return ResponseEntity.ok(CollectionModel.of(jobDataResource, links));
    }

    

    @DeleteMapping(value = "/{id}")
    public HttpEntity<MenuItemData> deleteItemData(@PathVariable("id") String id) {
    	menuItemDataService.deleteItemData(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping
    public HttpEntity<EntityModel<MenuItemData>> saveJobData(@Valid @RequestBody MenuItemData itemData) {
    	MenuItemData savedItemData = menuItemDataService.saveItemData(itemData);

        EntityModel<MenuItemData> itemDataResponse = EntityModel.of(new MenuItemData());
        itemDataResponse.add(
                linkTo(methodOn(MenuItemController.class).findItemData(savedItemData.getIdentifier())).withRel("itemData").expand());
        itemDataResponse
                .add(linkTo(methodOn(MenuItemController.class).findAllItems(null, null, INITIAL_PAGE, INITIAL_PAGE_SIZE))
                        .withRel("up"));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(itemData.getIdentifier()).toUri());

        return new ResponseEntity<>(itemDataResponse, httpHeaders, HttpStatus.CREATED);
    }
    
    @PatchMapping(value = "/{id}")
    public HttpEntity<EntityModel<MenuItemData>> patchJobData(@PathVariable("id") String id,
            @RequestBody MenuItemData itemData) {

    	menuItemDataService.patchItemData(id, itemData);
        EntityModel<MenuItemData> savedItemData = EntityModel.of(new MenuItemData());
        savedItemData.add(linkTo(methodOn(MenuItemController.class).findItemData(id)).withRel("itemData").expand());
        savedItemData.add(linkTo(methodOn(MenuItemController.class).findAllItems(null, null, INITIAL_PAGE, INITIAL_PAGE_SIZE))
                .withRel("items").expand());
        return ResponseEntity.ok(savedItemData);
    }
    
    @GetMapping(value = "/{id}")
    public HttpEntity<EntityModel<MenuItemData>> findItemData(@PathVariable("id") String id) {
    	MenuItemData itemData = menuItemDataService.findItemData(id);

        if (itemData == null) {
            return new ResponseEntity<EntityModel<MenuItemData>>(HttpStatus.OK);
        }

        EntityModel<MenuItemData> itemDataResponse = EntityModel.of(itemData);

        
        itemDataResponse.add(linkTo(methodOn(MenuItemController.class).findItemData(id)).withSelfRel().expand());

        itemDataResponse
                .add(linkTo(methodOn(MenuItemController.class).findAllItems(null, null, null, null)).withRel("up").expand());

        return ResponseEntity.ok(itemDataResponse);
    }
    
    
}
