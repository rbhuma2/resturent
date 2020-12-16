package com.core.service;

import java.util.List;

import com.core.model.MenuListResponse;
import com.core.model.Paging;
import com.core.mongo.data.entity.MenuItemData;

public interface MenuItemDataService {

    public MenuItemData findItemData(String id);

    public void deleteItemData(String id);

    public MenuItemData saveItemData(MenuItemData itemData);
    
    public void patchItemData(String id, MenuItemData menuItemData);

    public List<MenuListResponse> findItems(String filters, String query, int page, int size, Paging paging);

    

}
