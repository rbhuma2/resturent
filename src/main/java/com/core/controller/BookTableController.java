package com.core.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.core.model.Paging;
import com.core.mongo.data.entity.BookTableData;
import com.core.security.annotation.UserAuthorization;
import com.core.service.BookTableService;
import com.core.validator.BookTableValidator;

@RestController
@RequestMapping(value = "/v1/bookTable")
public class BookTableController {

    private static final int INITIAL_PAGE = 1;
    private static final int INITIAL_PAGE_SIZE = 10;

    @Autowired
    private BookTableService bookTableService;

    @Autowired
    private BookTableValidator bookTableValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(bookTableValidator);
    }
    
    @GetMapping
    public HttpEntity<CollectionModel<EntityModel<BookTableData>>> findAllJobs(
            @RequestParam(value = "filters", required = false) String filters,
            @RequestParam(value = "query", required = false) String query,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        int evalPageSize = size == null || size < 1 ? INITIAL_PAGE_SIZE : size;
        int evalPage = page == null || page < 1 ? INITIAL_PAGE : page;

        List<EntityModel<BookTableData>> jobDataResource = new ArrayList<>();
        List<Link> links = new ArrayList<>();

        Paging paging = new Paging();

        //List<JobData> jobDataList = bookTableService.findJobs(filters, query, evalPage, evalPageSize, paging);
        
        //if(jobDataList != null && !jobDataList.isEmpty()) {
        //    for (JobData jobData : jobDataList) {
				/*
				 * Link jobDetailLink =
				 * linkTo(methodOn(ReservationController.class).findJobData(jobData.getJobId()))
				 * .withRel("job") .expand(); jobDataResource.add(EntityModel.of(jobData,
				 * jobDetailLink));
				 * 
				 * Link item =
				 * linkTo(methodOn(ReservationController.class).findJobData(jobData.getJobId()))
				 * .withRel("item").expand(); links.add(item);
				 */
        //    }

            if (paging.getFirstPage() > 0) {
                links.add(linkTo(methodOn(BookTableController.class).findAllJobs(filters, query, paging.getFirstPage(),
                        paging.getPageSize())).withRel("first").expand());
            }
            if (paging.getLastPage() > 0) {
                links.add(linkTo(methodOn(BookTableController.class).findAllJobs(filters, query, paging.getLastPage(),
                        paging.getPageSize())).withRel("last").expand());
            }
            if (paging.getNextPage() > 0) {
                links.add(linkTo(methodOn(BookTableController.class).findAllJobs(filters, query, paging.getNextPage(),
                        paging.getPageSize())).withRel("next").expand());
            }
            if (paging.getPreviousPage() > 0) {
                links.add(linkTo(methodOn(BookTableController.class).findAllJobs(filters, query, paging.getPreviousPage(),
                        paging.getPageSize())).withRel("previous").expand());
            }
            if (paging.getCurrentPage() > 0) {
                links.add(linkTo(methodOn(BookTableController.class).findAllJobs(filters, query, paging.getCurrentPage(),
                        paging.getPageSize())).withRel("current").expand());
            }
        //}
        

        //Link self = linkTo(methodOn(ReservationController.class).findAllJobs(filters, query, evalPage, evalPageSize))
        //        .withSelfRel().expand();
        //links.add(self);

        return ResponseEntity.ok(CollectionModel.of(jobDataResource, links));
    }

    

    @DeleteMapping(value = "/{id}")
    public HttpEntity<BookTableData> deleteJobData(@PathVariable("id") String id) {
    	bookTableService.deleteBookTable(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @PostMapping
    public HttpEntity<EntityModel<BookTableData>> saveJobData(HttpServletRequest request, @RequestHeader HttpHeaders httpHeaders1, @Valid @RequestBody BookTableData bookTableData) {
    	BookTableData savedBookTableData = bookTableService.saveBookData(request.getRequestURI(), httpHeaders1, bookTableData);
    	
    	
    	EntityModel<BookTableData> bookTableResponse = EntityModel.of(new BookTableData());
        bookTableResponse.add(
                linkTo(methodOn(BookTableController.class).findBookData(savedBookTableData.getIdentifier())).withRel("bookTable").expand());
        bookTableResponse
                .add(linkTo(methodOn(BookTableController.class).findAllJobs(null, null, INITIAL_PAGE, INITIAL_PAGE_SIZE))
                        .withRel("up"));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(bookTableData.getIdentifier()).toUri());

        return new ResponseEntity<>(bookTableResponse, httpHeaders, HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{id}")
    public HttpEntity<EntityModel<BookTableData>> findBookData(@PathVariable("id") String id) {
    	BookTableData bookTableData = bookTableService.findBookTabeData(id);

        if (bookTableData == null) {
            return new ResponseEntity<EntityModel<BookTableData>>(HttpStatus.OK);
        }

        EntityModel<BookTableData> jobDataResponse = EntityModel.of(bookTableData);

        
        jobDataResponse.add(linkTo(methodOn(BookTableController.class).findBookData(id)).withSelfRel().expand());

        jobDataResponse
                .add(linkTo(methodOn(BookTableController.class).findAllJobs(null, null, null, null)).withRel("up").expand());

        return ResponseEntity.ok(jobDataResponse);
    }
    
    @GetMapping(value = "/{id}/approve")
    public HttpEntity<String> approveBooking(@PathVariable("id") String id) {
    	bookTableService.patchBookData(id);
        
        return ResponseEntity.ok("Approved");
    }
    
    
}
