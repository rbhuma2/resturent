package com.core.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.core.model.CartDataResponse;
import com.core.model.Paging;
import com.core.mongo.data.entity.CartData;
import com.core.mongo.data.entity.ItemData;
import com.core.mongo.data.entity.User;
import com.core.mongo.data.repository.CartDataRepository;
import com.core.mongo.data.repository.UserRepository;
import com.core.service.ItemDataService;
import com.core.service.OrderStatusService;
import com.core.utils.PageNavigation;

@Service
public class OrderStatusServiceImpl implements OrderStatusService{
	
	@Autowired
	private CartDataRepository cartDataRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ItemDataService itemDataService;
	
	@Override
	public List<CartDataResponse> findCardDataResponse(Paging paging, int page, int size, String email) {
		
		List<CartDataResponse> cartDataResponseList = new ArrayList<>();
		Page<CartData> cartDataListPage = null;
		Pageable pageable = PageRequest.of(page - 1, size, Direction.ASC, "orderPlaceDate");
		
		User user = userRepository.findByEmail(email);
		
		if(user != null && !user.isAdmin()) {
			return cartDataResponseList;
		}
		cartDataListPage = cartDataRepository.findByIsProcessed(false, pageable);
		
		if (cartDataListPage != null && cartDataListPage.getTotalElements() != 0) {
            for (CartData cartData : cartDataListPage) {
            	CartDataResponse response = new CartDataResponse();
            	response.setIdentifier(cartData.getIdentifier());
            	response.setTotalItems(cartData.getTotalItems());
            	response.setTotalAmount(cartData.getTotalAmount());
            	response.setOrderPlaceDate(cartData.getOrderPlaceDate());
            	User user1 = userRepository.findByEmail(cartData.getEmail());
            	if(user1 != null) {
            		response.setName(user1.getName());
            		response.setPhoneNumber(user1.getPhoneNumber());
            	}
            	cartDataResponseList.add(response);
            }
        } else {
            return cartDataResponseList;
        }

        int totalRows = (int) cartDataListPage.getTotalElements();

        Paging pageNavigation = PageNavigation.derivePageAttributes(totalRows, size, page);
        paging.setCurrentPage(pageNavigation.getCurrentPage());
        paging.setFirstPage(pageNavigation.getFirstPage());
        paging.setLastPage(pageNavigation.getLastPage());
        paging.setNextPage(pageNavigation.getNextPage());
        paging.setPreviousPage(pageNavigation.getPreviousPage());
        paging.setPageSize(size);
        return cartDataResponseList;
	}

	@Override
	public CartDataResponse findOrderDetails(String id) {
		CartDataResponse response = null;
		CartData cartData = cartDataRepository.findById(id).orElse(null);
		
		if(cartData != null) {
			response = new CartDataResponse();
			response.setIdentifier(cartData.getIdentifier());
			response.setEmail(cartData.getEmail());
			response.setOrderPlaceDate(cartData.getOrderPlaceDate());
			BigDecimal taxAmount = BigDecimal.valueOf(cartData.getTax()).setScale(2, RoundingMode.HALF_EVEN);
			//response.setTax(taxAmount.doubleValue());
			BigDecimal totalAmount = BigDecimal.valueOf(cartData.getTotalAmount()).setScale(2, RoundingMode.HALF_EVEN);
			response.setTotalAmount(totalAmount.doubleValue());
			BigDecimal subTotal = BigDecimal.valueOf(cartData.getSubTotalAmount()).setScale(2, RoundingMode.HALF_EVEN);
			//response.setSubTotal(subTotal.doubleValue());
			response.setTotalItems(cartData.getTotalItems());
			if(cartData.getItemList() !=null && !cartData.getItemList().isEmpty()) {
				response.setItemList(new ArrayList<>());
				for(String item : cartData.getItemList()) {
					ItemData existingItem = itemDataService.findItemData(item);
					if(existingItem != null) {
						response.getItemList().add(existingItem);
					}
				}
			}
			
		}
		return response;
	}

}
