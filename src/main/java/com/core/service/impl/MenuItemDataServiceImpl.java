package com.core.service.impl;

import static com.core.utils.CommonConstants.CHAR_COLON;
import static com.core.utils.CommonConstants.CHAR_COMMA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.core.exception.application.DataNotFoundException;
import com.core.model.MenuListResponse;
import com.core.model.Paging;
import com.core.mongo.data.entity.MenuItemData;
import com.core.mongo.data.repository.MenuItemDataRepository;
import com.core.service.MenuItemDataService;
import com.core.utils.MultiFiltersSearch;
import com.core.utils.PageNavigation;

@Service
public class MenuItemDataServiceImpl implements MenuItemDataService {

    @Autowired
    private MenuItemDataRepository itemDataRepository;

    
	@Override
	public MenuItemData findItemData(String id) {
		return itemDataRepository.findById(id).orElse(null);
	}

	@Override
	public void deleteItemData(String id) {
		itemDataRepository.deleteById(id);
		
	}

	@Override
	public MenuItemData saveItemData(MenuItemData itemData) {
		MenuItemData savediftCardData = itemDataRepository.save(itemData);
		MenuItemData response = new MenuItemData();
        response.setIdentifier(savediftCardData.getIdentifier());
        return response;
	}

	@Override
	public void patchItemData(String id, MenuItemData itemData) {
		
		MenuItemData existingItemData = findItemData(id);
        if (existingItemData == null) {
            throw new DataNotFoundException("no.item.exists", new Object[] { id });
        }
        if (itemData.getName() != null) {
        	existingItemData.setName(itemData.getName());
        }
        
        if (itemData.getAmount()  != 0) {
        	existingItemData.setAmount(itemData.getAmount());
        }

        if (itemData.getCategory() != null) {
        	existingItemData.setCategory(itemData.getCategory());
        }
        
        if (itemData.getType() != null) {
        	existingItemData.setType(itemData.getType());
        }
        
        if (itemData.getDescription() != null) {
        	existingItemData.setDescription(itemData.getDescription());
        }

        

        itemDataRepository.save(existingItemData);
		
	}

	@Override
	public List<MenuListResponse> findItems(String filters, String query, int page, int size, Paging paging) {
		
		List<MenuListResponse> itemDataList = new ArrayList<>();
        Page<MenuItemData> itemDataListPage = null;

        Map<String, List<String>> matchFilterMap = new HashMap<>();
        if (filters != null) {
            matchFilterMap = MultiFiltersSearch.getFiltersMatchList(filters, matchFilterMap);
        }
        
        Pageable pageable = PageRequest.of(page - 1, size, Direction.ASC, "category", "type", "name");
        List<String> type = null;
        List<String> category = null;
        if (matchFilterMap.get("type") != null) {
        	type = matchFilterMap.get("type");
        }
        if (matchFilterMap.get("category") != null) {
        	category = matchFilterMap.get("category");
        }
        
        if (type != null && category != null && query != null) { 
        	itemDataListPage = itemDataRepository.findByNameLikeIgnoreCaseAndTypeInAndCategoryIn(query, type, category, pageable);
        }else if(type != null && category != null) {
        	itemDataListPage = itemDataRepository.findByTypeInAndCategoryIn(type, category, pageable);
        }else if(type != null && query != null) {
        	itemDataListPage = itemDataRepository.findByNameLikeIgnoreCaseAndTypeIn(query, type, pageable);
        }else if(category != null && query != null) {
        	itemDataListPage = itemDataRepository.findByNameLikeIgnoreCaseAndCategoryIn(query, category, pageable);
        }else if(query != null) {
        	itemDataListPage = itemDataRepository.findByNameLikeIgnoreCase(query, pageable);
        }else if(type != null){
        	itemDataListPage = itemDataRepository.findByTypeIn(type, pageable);
        }else if(category != null) {
        	itemDataListPage = itemDataRepository.findByCategoryIn(category, pageable);
        }else {
        	itemDataListPage = itemDataRepository.findAll(pageable);
        }
        
        Map<String, List<MenuItemData>> menuItemMap = new HashMap<>();
        if (itemDataListPage != null && itemDataListPage.getTotalElements() != 0) {
            for (MenuItemData itemData : itemDataListPage) {
            	MenuItemData itemData1 = new MenuItemData();
            	itemData1.setAmount(itemData.getAmount());
            	itemData1.setCategory(itemData.getCategory());
            	itemData1.setDescription(itemData.getDescription());
            	itemData1.setImage(itemData.getImage());
            	itemData1.setName(itemData.getName());
            	itemData1.setSpiceLevel(itemData.isSpiceLevel());
            	itemData1.setSubType(itemData.getSubType());
            	itemData1.setType(itemData.getType());;
            	List<MenuItemData> menuDataList = null;
            	if(menuItemMap.containsKey(itemData1.getType())) {
            		menuDataList = menuItemMap.get(itemData1.getType());
            		menuDataList.add(itemData1);
            	}else {
            		menuDataList = new ArrayList<>();
            		menuDataList.add(itemData1);
            	}
            	
            	menuItemMap.put(itemData1.getType(), menuDataList);
            }
        } else {
            return itemDataList;
        }
        
        menuItemMap.forEach((key, value) -> {
        	MenuListResponse response = new MenuListResponse();
        	response.setType(key);
        	response.setItemList(value);
        	itemDataList.add(response);
        });
        
        return itemDataList;
	}
	
	/*private Map<String, List<String>> getFilters(String filters, Map<String, List<String>> filtersMap) {
        String[] matchFilters = filters.split(String.valueOf(CHAR_COMMA));
        List<String> dataList = null;
        for (int index = 0; index < matchFilters.length; index++) {
            String[] matchFilter = matchFilters[index].split(String.valueOf(CHAR_COLON));
            if (matchFilter.length > 1 && !matchFilter[0].trim().isEmpty() && !matchFilter[1].trim().isEmpty()) {
                String filter = matchFilter[0].trim();
                String match = matchFilter[1].trim();
                if(filtersMap.containsKey(filter)) {
                	dataList = filtersMap.get(filter);
                }else {
                	dataList = new ArrayList<>();
                }
                dataList.add(match);
                filtersMap.put(filter, dataList);
            } else if (matchFilter.length == 1 && !matchFilter[0].trim().isEmpty()) {
                String filter = matchFilter[0].trim();
                filtersMap.put(filter, null);
            }
        }
        return filtersMap;

    }*/

}
