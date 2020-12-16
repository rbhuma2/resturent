package com.core.service.impl;

import static com.core.utils.CommonConstants.SHORT_ZERO;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.exception.application.DataNotFoundException;
import com.core.mongo.data.entity.ItemData;
import com.core.mongo.data.repository.ItemDataRepository;
import com.core.service.CartDataService;
import com.core.service.ItemDataService;

@Service
public class ItemDataServiceImpl implements ItemDataService {

	@Autowired
	private ItemDataRepository itemDataRepository;
	
	@Autowired
	private CartDataService cartDataService;
	
	@Override
	public ItemData saveToCart(ItemData itemData, String email) {
		
		itemDataRepository.save(itemData);
		
		cartDataService.saveCartData(email, itemData);
		
		
		return itemData;
	}
	
	@Override
	public void deleteItem(String id) {
		itemDataRepository.deleteById(id);
		
	}

	@Override
	public ItemData findItemData(String id) {
		return itemDataRepository.findById(id).orElse(null);
	}

	@Override
	public void patchItemData(String email, ItemData itemData) {
		
		if(itemData.getQuantity() == SHORT_ZERO) {
			deleteItem(itemData.getIdentifier());
		}else {
			ItemData existingData = findItemData(itemData.getIdentifier());
			
			if(existingData == null) {
				throw new DataNotFoundException("no.item.exists", new Object[] { itemData.getIdentifier() });
			}
			
			if(itemData.isOnlyQuantity()) {
				
				double actulAmount = BigDecimal.valueOf(existingData.getNetAmount()/existingData.getQuantity()).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
				existingData.setQuantity(itemData.getQuantity());
				itemData.setNetAmount(BigDecimal.valueOf(actulAmount * itemData.getQuantity()).setScale(2, RoundingMode.HALF_EVEN).doubleValue());
				
			}
			itemDataRepository.save(itemData);
			
		}
		
		cartDataService.saveCartData(email, itemData);
	}
	

}
