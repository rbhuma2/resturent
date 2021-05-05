package com.core.mongo.data.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.core.mongo.data.entity.MenuItemData;

public interface MenuItemDataRepository extends MongoRepository<MenuItemData, String> {

	Page<MenuItemData> findByTypeIn(List<String> type, Pageable pageable);
	
	Page<MenuItemData> findByCategoryIn(List<String> category, Pageable pageable);
	
	Page<MenuItemData> findByItemIn(List<String> item, Pageable pageable);
		
	Page<MenuItemData> findByNameLikeIgnoreCase(String query, Pageable pageable);
	
	Page<MenuItemData> findByTypeInAndCategoryIn(List<String> type, List<String> category, Pageable pageable);
	
	Page<MenuItemData> findByItemInAndCategoryIn(List<String> item, List<String> category, Pageable pageable);
		
	Page<MenuItemData> findByNameLikeIgnoreCaseAndTypeIn(String query, List<String> type, Pageable pageable);
	
	Page<MenuItemData> findByNameLikeIgnoreCaseAndCategoryIn(String query, List<String> category, Pageable pageable);
	
	Page<MenuItemData> findByNameLikeIgnoreCaseAndTypeInAndCategoryIn(String query, List<String> type, List<String> category, Pageable pageable);
	
	Page<MenuItemData> findByNameLikeIgnoreCaseAndTypeInAndCategoryInAndItemIn(String query, List<String> type, List<String> category, List<String> item, Pageable pageable);
}
