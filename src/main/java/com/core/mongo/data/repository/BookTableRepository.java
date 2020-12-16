package com.core.mongo.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.core.mongo.data.entity.BookTableData;

public interface BookTableRepository extends MongoRepository<BookTableData, String> {

  BookTableData findByIdentifierAndStatus(String identifier, String status);   
}
