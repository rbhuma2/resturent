package com.core.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.core.exception.application.DataNotFoundException;
import com.core.mongo.data.entity.BookTableData;
import com.core.mongo.data.repository.BookTableRepository;
import com.core.service.BookTableService;
import com.core.utils.DateRoutine;
import com.core.utils.EmailNotification;

@Service
public class BookTableServiceImpl implements BookTableService{

	@Autowired
    private BookTableRepository bookTableRepository;
	
	@Autowired
	private EmailNotification emailNotification;
	
	@Value("${email.notification.enable}")
    private String isNotificationEnable;
	
	@Override
	public BookTableData findBookTabeData(String id) {
		
		return bookTableRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteBookTable(String id) {
		bookTableRepository.deleteById(id);
		
	}

	@Override
	public BookTableData saveBookData(String pathUrl, HttpHeaders httpHeaders, BookTableData bookTable) {
		bookTable.setStatus("Pending");
		BookTableData savedbBookedData = bookTableRepository.save(bookTable);
		BookTableData response = new BookTableData();
        response.setIdentifier(savedbBookedData.getIdentifier());
        if(Boolean.valueOf(isNotificationEnable)) {
        	emailNotification.notifyBookingStatus(pathUrl, httpHeaders, savedbBookedData);
        }
        
        return response;
	}
	
	@Override
	public void patchBookData(String id) {
		
		BookTableData bookTable = findBookTabeData(id);
		//BookTableData bookTable = bookTableRepository.findByIdentifierAndStatus(id, "Pending");
		
		if(bookTable == null || !bookTable.getStatus().equals("Pending") || bookTable.getDate().compareTo(DateRoutine.currentDateAsStr()) < 0 ) {
			throw new DataNotFoundException("no.data.found");
		}
		
		bookTable.setStatus("Approved");
		bookTableRepository.save(bookTable);
		
		if(Boolean.valueOf(isNotificationEnable)) {
        	emailNotification.notifyBookingStatus(null, null, bookTable);
        }
	}

}
