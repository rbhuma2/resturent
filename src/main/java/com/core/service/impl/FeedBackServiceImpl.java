package com.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.core.model.Paging;
import com.core.mongo.data.entity.FeedBack;
import com.core.mongo.data.repository.FeedBackRepository;
import com.core.service.FeedBackService;
import com.core.utils.PageNavigation;

@Service
public class FeedBackServiceImpl implements FeedBackService{

	@Autowired
    private FeedBackRepository feedBackRepository;
	
	@Override
	public List<FeedBack> findAllFeedBacks(int page, int size, Paging paging) {
		
		List<FeedBack> feedBackList = new ArrayList<>();
        
		Pageable pageable = PageRequest.of(page - 1, size, Direction.DESC, "date");
		
		Page<FeedBack> feedBackListPage = feedBackRepository.findAll(pageable);
		
		if (feedBackListPage.getTotalElements() != 0) {
            for (FeedBack feedBack : feedBackListPage) {
            	feedBackList.add(feedBack);
            }
        } else {
            return feedBackList;
        }

        int totalRows = (int) feedBackListPage.getTotalElements();

        Paging pageNavigation = PageNavigation.derivePageAttributes(totalRows, size, page);
        paging.setCurrentPage(pageNavigation.getCurrentPage());
        paging.setFirstPage(pageNavigation.getFirstPage());
        paging.setLastPage(pageNavigation.getLastPage());
        paging.setNextPage(pageNavigation.getNextPage());
        paging.setPreviousPage(pageNavigation.getPreviousPage());
        paging.setPageSize(size);
        return feedBackList;
	}

	@Override
	public void deleteFeedBack(String id) {
		feedBackRepository.deleteById(id);
		
	}

	@Override
	public FeedBack saveFeedBAck(FeedBack feedBack) {
		FeedBack savedbFeedBack = feedBackRepository.save(feedBack);
		FeedBack response = new FeedBack();
        response.setIdentifier(savedbFeedBack.getIdentifier());
        return response;
	}

}
