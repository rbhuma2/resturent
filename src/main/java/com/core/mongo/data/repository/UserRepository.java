package com.core.mongo.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.core.mongo.data.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findByEmailAndPassword(String email, String password);
	
	User findByPhoneNumberAndPassword(String phoneNumber, String password);
	
	User findByEmail(String email);

    
}
