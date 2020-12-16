package com.core.service;

import java.util.List;

import com.core.model.Paging;
import com.core.mongo.data.entity.FeedBack;

public interface FeedBackService {

    public List<FeedBack> findAllFeedBacks(int page, int size, Paging paging);

    public void deleteFeedBack(String id);

    public FeedBack saveFeedBAck(FeedBack feedBack);

    

}
