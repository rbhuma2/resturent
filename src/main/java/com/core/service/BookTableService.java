package com.core.service;

import org.springframework.http.HttpHeaders;

import com.core.mongo.data.entity.BookTableData;

public interface BookTableService {

    public BookTableData findBookTabeData(String id);

    public void deleteBookTable(String id);

    public BookTableData saveBookData(String pathUrl, HttpHeaders httpHeaders, BookTableData bookTable);
    
    public void patchBookData(String id);

    

}
