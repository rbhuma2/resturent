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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.core.model.Paging;
import com.core.mongo.data.entity.FeedBack;
import com.core.service.FeedBackService;
import com.core.validator.FeedBackValidator;

@RestController
@RequestMapping(value = "/v1/feedBack")
public class FeedBackController {

    private static final int INITIAL_PAGE = 1;
    private static final int INITIAL_PAGE_SIZE = 10;

    @Autowired
    private FeedBackService feedBackService;

    @Autowired
    private FeedBackValidator feedBackValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(feedBackValidator);
    }
    
    @GetMapping
    public HttpEntity<CollectionModel<EntityModel<FeedBack>>> findAllFeedBacks(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        int evalPageSize = size == null || size < 1 ? INITIAL_PAGE_SIZE : size;
        int evalPage = page == null || page < 1 ? INITIAL_PAGE : page;

        List<EntityModel<FeedBack>> feedBackResource = new ArrayList<>();
        List<Link> links = new ArrayList<>();

        Paging paging = new Paging();

        List<FeedBack> feedBackList = feedBackService.findAllFeedBacks(evalPage, evalPageSize, paging);
        
        if(feedBackList != null && !feedBackList.isEmpty()) {
        	for (FeedBack feedBack : feedBackList) {
        		feedBackResource.add(EntityModel.of(feedBack));
        	}
            
        	if (paging.getFirstPage() > 0) {
                links.add(linkTo(methodOn(FeedBackController.class).findAllFeedBacks(paging.getFirstPage(),
                        paging.getPageSize())).withRel("first").expand());
            }
            if (paging.getLastPage() > 0) {
                links.add(linkTo(methodOn(FeedBackController.class).findAllFeedBacks(paging.getLastPage(),
                        paging.getPageSize())).withRel("last").expand());
            }
            if (paging.getNextPage() > 0) {
                links.add(linkTo(methodOn(FeedBackController.class).findAllFeedBacks(paging.getNextPage(),
                        paging.getPageSize())).withRel("next").expand());
            }
            if (paging.getPreviousPage() > 0) {
                links.add(linkTo(methodOn(FeedBackController.class).findAllFeedBacks(paging.getPreviousPage(),
                        paging.getPageSize())).withRel("previous").expand());
            }
            if (paging.getCurrentPage() > 0) {
                links.add(linkTo(methodOn(FeedBackController.class).findAllFeedBacks(paging.getCurrentPage(),
                        paging.getPageSize())).withRel("current").expand());
            }
        }
        

        Link self = linkTo(methodOn(FeedBackController.class).findAllFeedBacks(evalPage, evalPageSize))
                .withSelfRel().expand();
        links.add(self);

        return ResponseEntity.ok(CollectionModel.of(feedBackResource, links));
    }

    

    @DeleteMapping(value = "/{id}")
    public HttpEntity<FeedBack> deleteJobData(@PathVariable("id") String id) {
    	feedBackService.deleteFeedBack(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping
    public HttpEntity<EntityModel<FeedBack>> saveFeedBackData(@Valid @RequestBody FeedBack feedBackData) {
    	FeedBack savedFeedBack = feedBackService.saveFeedBAck(feedBackData);

        EntityModel<FeedBack> feedBackResponse = EntityModel.of(new FeedBack());
        feedBackResponse
                .add(linkTo(methodOn(FeedBackController.class).findAllFeedBacks(INITIAL_PAGE, INITIAL_PAGE_SIZE))
                        .withRel("up"));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedFeedBack.getIdentifier()).toUri());

        return new ResponseEntity<>(feedBackResponse, httpHeaders, HttpStatus.CREATED);
    }
    
        
    
}
