package com.core.mongo.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.core.mongo.data.entity.ItemData;

public interface ItemDataRepository extends MongoRepository<ItemData, String> {

	//CartData findByEmail(String email);

    
}
