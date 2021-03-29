package com.core.service;

import java.util.List;

import com.core.model.CartDataResponse;
import com.core.model.Paging;

public interface OrderStatusService {

	List<CartDataResponse> findCardDataResponse(Paging paging, int page, int size, String email);

	CartDataResponse findOrderDetails(String id);

}
