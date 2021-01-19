package com.core.service;

import com.core.mongo.data.entity.ItemData;

public interface ItemDataService {

    public ItemData saveToCart(ItemData  itemData, String email);

	public void deleteItem(String id);
	
	public ItemData findItemData(String id);
	
	public void patchItemData(String email, ItemData  itemData);
    
}
