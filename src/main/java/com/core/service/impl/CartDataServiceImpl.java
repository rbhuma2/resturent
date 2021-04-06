package com.core.service.impl;

import static com.core.utils.CommonConstants.SHORT_ZERO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.exception.application.DataNotFoundException;
import com.core.model.CartDataResponse;
import com.core.mongo.data.entity.CartData;
import com.core.mongo.data.entity.ItemData;
import com.core.mongo.data.repository.CartDataRepository;
import com.core.service.CartDataService;
import com.core.service.ItemDataService;
import com.core.utils.CommonConstants;
import com.core.utils.DateRoutine;
import com.core.utils.MultiFiltersSearch;

@Service
public class CartDataServiceImpl implements CartDataService{

	@Autowired
	private CartDataRepository cartDataRepository;
	
	@Autowired
	private ItemDataService itemDataService;
	
	
	@Override
	public CartData findCardData(String email) {
		
		
		return cartDataRepository.findByEmailAndOrderPlaceDate(email, DateRoutine.dateTimeAsYYYYMMDDHHhhmmssSSSString(DateRoutine.defaultDateTimestamp()));
		
	}
	
	
	@Override
	public CartDataResponse findCardDataResponse(String email) {
		
		CartDataResponse response = null;
		CartData cartData = findCardData(email);
		
		if(cartData != null) {
			response = new CartDataResponse();
			response.setIdentifier(cartData.getIdentifier());
			response.setEmail(cartData.getEmail());
			response.setOrderPlaceDate(cartData.getOrderPlaceDate());
			BigDecimal taxAmount = BigDecimal.valueOf(cartData.getTax()).setScale(2, RoundingMode.HALF_EVEN);
			response.setTax(taxAmount.doubleValue());
			BigDecimal totalAmount = BigDecimal.valueOf(cartData.getTotalAmount()).setScale(2, RoundingMode.HALF_EVEN);
			response.setTotalAmount(totalAmount.doubleValue());
			BigDecimal subTotal = BigDecimal.valueOf(cartData.getSubTotalAmount()).setScale(2, RoundingMode.HALF_EVEN);
			response.setSubTotal(subTotal.doubleValue());
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
	
	@Override
	public void saveCartData(String email, ItemData itemData) {
		
		CartData cartData = findCardData(email);
		
		if(cartData == null) {
			CartData newCartData = buildCartData(email, cartData, itemData);
			cartDataRepository.save(newCartData);
		}else {
			buildCartData(cartData, itemData);
		}
		
		
	}
	
	private void buildCartData(CartData cartData, ItemData itemData) {
		
		double totalAmount = CommonConstants.DECIMAL_ZERO;
		if(itemData.getQuantity() == SHORT_ZERO) {
			if(cartData.getItemList().contains(itemData.getIdentifier())) {
				cartData.getItemList().remove(itemData.getIdentifier());
			}
		}else {
			if(!cartData.getItemList().contains(itemData.getIdentifier())) {
				cartData.getItemList().add(itemData.getIdentifier());
			}
			
		}  
				
		if(cartData.getItemList().isEmpty()) {
			deleteCart(cartData.getIdentifier());
		}else {
			for(String item : cartData.getItemList()) {
				ItemData existingItem = itemDataService.findItemData(item);
				if(existingItem != null) {
					totalAmount += existingItem.getNetAmount();
				}
			}
			cartData.setSubTotalAmount(totalAmount);
			cartData.setTax(cartData.getSubTotalAmount() *0.2);
			cartData.setTotalAmount(cartData.getSubTotalAmount() + cartData.getTax());
			cartDataRepository.save(cartData);
		}
		            
	}

	private CartData buildCartData(String email, CartData cartData, ItemData itemData) {
		
		if(cartData == null) {
			cartData = new CartData();
			cartData.setOrderPlaceDate(DateRoutine.dateTimeAsYYYYMMDDHHhhmmssSSSString(DateRoutine.defaultDateTimestamp()));
		}
		
		cartData.setEmail(email);
		if(cartData.getItemList() ==null) {
			cartData.setItemList(new ArrayList<>());
		}
		
		cartData.getItemList().add(itemData.getIdentifier());
		cartData.setTotalItems(cartData.getItemList().size());
		
		double totalAmount = CommonConstants.DECIMAL_ZERO;
		
		for(String item : cartData.getItemList()) {
			ItemData existingItem = itemDataService.findItemData(item);
			if(existingItem != null) {
				totalAmount += existingItem.getNetAmount();
			}
		}
		cartData.setSubTotalAmount(totalAmount);
		cartData.setTax(cartData.getSubTotalAmount() *0.2);
		cartData.setTotalAmount(cartData.getSubTotalAmount() + cartData.getTax());
		return cartData;
	}

	@Override
	public void deleteCart(String id) {
		cartDataRepository.deleteById(id);
	}

	@Override
	public void deleteItem(String email, String filters) {
		
		
		
		CartData existingCartData = findCardData(email);
		if(existingCartData == null ) {
			throw new DataNotFoundException("no.data.found");
		}
		
		Map<String, String> matchFilterMap = new HashMap<>();
		matchFilterMap = MultiFiltersSearch.getFilters(filters, matchFilterMap);
		
		
		
		String type = null;
		String subType = null;
		String name = null;
		if (matchFilterMap.get("type") != null) {
        	type = matchFilterMap.get("type");
        }
		
		if (matchFilterMap.get("subType") != null) {
			subType = matchFilterMap.get("subType");
        }
		
		if (matchFilterMap.get("name") != null) {
			name = matchFilterMap.get("name");
        }
		
		CartData newCartData = deleteItemData(existingCartData, name+type+subType);
		cartDataRepository.save(newCartData);
		
	}

	private CartData deleteItemData(CartData existingCartData, String itemValue) {
			
		
		/*Map<String, Item> itemDataMap = new HashMap<>();
		existingCartData.getItemList().forEach(item -> {
			itemDataMap.put(item.getName().trim()+item.getType().trim()+item.getSubType().trim(), item);
		});
		
		itemDataMap.remove(itemValue);
		
		CartData newCartData = new CartData();
		newCartData.setIdentifier(existingCartData.getIdentifier());
		newCartData.setEmail(existingCartData.getEmail());
		newCartData.setExpirationDate(existingCartData.getExpirationDate());
		
		itemDataMap.forEach((key, value) -> {
			if(newCartData.getItemList() == null) {
				newCartData.setItemList(new ArrayList<>());
			}
			newCartData.getItemList().add(value);
		});
		
		newCartData.getItemList().forEach(item -> {
			newCartData.setTotalAmount(newCartData.getTotalAmount() + item.getAmount());
			newCartData.setTotalItems(newCartData.getTotalItems() + item.getQuantity());
		});
		
		newCartData.setTax(newCartData.getTotalAmount() *  0.2);
		return newCartData;*/
		return null;
		
	}

	@Override
	public void saveCardData(String email, String id, CartData  cartData) {
		/*CartData existingCartData = findCardData(email);
		if(existingCartData == null ) {
			throw new DataNotFoundException("no.data.found");
		}*/
		
		CartData existingCartData = cartDataRepository.findById(id).orElse(null);
		if(existingCartData == null ) {
			throw new DataNotFoundException("no.data.found");
		}
		existingCartData.setProcessed(cartData.isProcessed());
		existingCartData.setTransactionId(cartData.getTransactionId());
		existingCartData.setOrderPlaceDate(DateRoutine.dateTimeAsYYYYMMDDHHhhmmssSSSString(DateRoutine.currentTimestamp()));
		cartDataRepository.save(existingCartData);
		
		
	}

	


	

	

}
