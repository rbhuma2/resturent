package com.core.mongo.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.core.mongo.data.entity.FeedBack;

public interface FeedBackRepository extends MongoRepository<FeedBack, String> {

    
}
