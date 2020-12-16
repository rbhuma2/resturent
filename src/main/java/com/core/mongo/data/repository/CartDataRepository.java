package com.core.mongo.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.core.mongo.data.entity.CartData;

public interface CartDataRepository extends MongoRepository<CartData, String> {

	CartData findByEmail(String email);

    
}
