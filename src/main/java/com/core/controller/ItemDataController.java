package com.core.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static com.core.utils.CommonConstants.BLANK_STRING;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.core.exception.application.InvalidDataException;
import com.core.mongo.data.entity.CartData;
import com.core.mongo.data.entity.ItemData;
import com.core.service.ItemDataService;
import com.core.utils.IdGenerator;
import com.core.validator.ItemDataValidator;

@RestController
@RequestMapping(value = "/v1/item")
public class ItemDataController {

    private static final String  USER_ID = "X-User-Id";
	
	@Autowired
    private ItemDataService itemDataService;

    @Autowired
    private ItemDataValidator itemDataValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(itemDataValidator);
    }
       
    

    @DeleteMapping(value = "/{id}")
    public HttpEntity<CartData> deleteItemData(@RequestHeader HttpHeaders httpHeaders, @PathVariable("id") String id) {
    	List<String> emailList = httpHeaders.getValuesAsList(USER_ID);
    	if(emailList.isEmpty()) {
    		throw new InvalidDataException("bad.access.data");
    	}
    	itemDataService.deleteItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping
    public HttpEntity<EntityModel<ItemData>> saveItemData(@RequestHeader HttpHeaders httpHeaders, @Valid @RequestBody ItemData itemData) {
    	List<String> emailList = httpHeaders.getValuesAsList(USER_ID);
    	String email =emailList.isEmpty() ? IdGenerator.generateUniqueStringUpperCase(36) : emailList.get(0);
    	
    	ItemData savedItemData = itemDataService.saveToCart(itemData, email);
    	ItemData savedItemData1 = new ItemData();
    	savedItemData1.setEmail(email);

        EntityModel<ItemData> itemDataResponse = EntityModel.of(savedItemData1);
        
        itemDataResponse.add(
                linkTo(methodOn(ItemDataController.class).findItemData(savedItemData.getIdentifier())).withRel("itemData").expand());
        //HttpHeaders httpHeader = new HttpHeaders();
        //httpHeader.add(USER_ID, email);
        
        return new ResponseEntity<>(itemDataResponse, HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{id}")
    public HttpEntity<EntityModel<ItemData>> findItemData(@PathVariable("id") String id) {
    	
    	
    	ItemData itemData = itemDataService.findItemData(id);

        if (itemData == null) {
            return new ResponseEntity<EntityModel<ItemData>>(HttpStatus.OK);
        }

        EntityModel<ItemData> itemDataResponse = EntityModel.of(itemData);

        
        itemDataResponse.add(linkTo(methodOn(ItemDataController.class).findItemData(id)).withSelfRel().expand());

        return ResponseEntity.ok(itemDataResponse);
    }
    
    @PatchMapping(value = "/{id}")
    public HttpEntity<EntityModel<ItemData>> patchJobData(@PathVariable("id") String id, @RequestHeader HttpHeaders httpHeaders,
    		@RequestBody ItemData itemData) {

    	List<String> emailList = httpHeaders.getValuesAsList(USER_ID);
    	if(emailList.isEmpty()) {
    		throw new InvalidDataException("bad.access.data");
    	}
    	itemData.setIdentifier(id);
    	itemDataService.patchItemData(emailList.get(0), itemData);
        EntityModel<ItemData> savedItemData = EntityModel.of(new ItemData());
        savedItemData.add(linkTo(methodOn(ItemDataController.class).findItemData(id)).withRel("itemData").expand());
        return ResponseEntity.ok(savedItemData);
    }
    
    
}
