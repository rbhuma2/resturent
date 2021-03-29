package com.core.service;

import com.core.model.CartDataResponse;
import com.core.mongo.data.entity.CartData;
import com.core.mongo.data.entity.ItemData;

public interface CartDataService {

    public void deleteItem(String email, String filters);
	
	public CartData findCardData(String email);
	
	public void saveCardData(String email, String id, CartData  cartData);
	
	public void deleteCart(String id);
	
	public void saveCartData(String email, ItemData itemData);
	
	public CartDataResponse findCardDataResponse(String email);
    
}
