package com.core.mongo.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.core.mongo.data.entity.GiftCard;

public interface GiftCardRepository extends MongoRepository<GiftCard, String> {

    
}
