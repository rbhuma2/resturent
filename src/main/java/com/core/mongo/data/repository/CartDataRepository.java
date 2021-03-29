package com.core.mongo.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.core.mongo.data.entity.CartData;

public interface CartDataRepository extends MongoRepository<CartData, String> {

	CartData findByEmailAndOrderPlaceDate(String email, String orderPlaceDate);
	
	Page<CartData> findByOrderPlaceDateNotAndIsProcessed(String orderPlaceDate, boolean isProcessed, Pageable pageable);

    
}
