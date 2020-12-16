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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.core.mongo.data.entity.BookTableData;
import com.core.mongo.data.entity.GiftCard;
import com.core.service.GiftCardService;
import com.core.validator.GiftCardValidator;

@RestController
@RequestMapping(value = "/v1/giftCard")
public class GiftCardController {

    @Autowired
    private GiftCardService giftCardService;

    @Autowired
    private GiftCardValidator giftCardValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(giftCardValidator);
    }
    
    @DeleteMapping(value = "/{id}")
    public HttpEntity<BookTableData> deleteGiftCard(@PathVariable("id") String id) {
    	giftCardService.deleteGiftCard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    //@UserAuthorization("createJob")
    @PostMapping
    public HttpEntity<EntityModel<GiftCard>> saveGiftCardData(@Valid @RequestBody GiftCard giftCard) {
    	GiftCard savedGiftCardData = giftCardService.saveGiftCardData(giftCard);

        EntityModel<GiftCard> giftCardResponse = EntityModel.of(new GiftCard());
        giftCardResponse.add(
                linkTo(methodOn(GiftCardController.class).findGiftCard(savedGiftCardData.getIdentifier())).withRel("giftCard").expand());
        
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(giftCard.getIdentifier()).toUri());

        return new ResponseEntity<>(giftCardResponse, httpHeaders, HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{id}")
    public HttpEntity<EntityModel<GiftCard>> findGiftCard(@PathVariable("id") String id) {
    	GiftCard giftCard = giftCardService.findGiftCardData(id);

        if (giftCard == null) {
            return new ResponseEntity<EntityModel<GiftCard>>(HttpStatus.OK);
        }

        EntityModel<GiftCard> giftCardResponse = EntityModel.of(giftCard);

        
        giftCardResponse.add(linkTo(methodOn(GiftCardController.class).findGiftCard(id)).withSelfRel().expand());

        
        return ResponseEntity.ok(giftCardResponse);
    }
    
    
}
