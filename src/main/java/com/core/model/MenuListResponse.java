package com.core.model;

import java.util.LinkedList;
import java.util.List;

import com.core.mongo.data.entity.MenuItemData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
public class MenuListResponse {
	
	private String type;
	private List<MenuItemData> itemList = new LinkedList<>();
	
	public MenuListResponse() {
		super();
	}

	public MenuListResponse(String type, List<MenuItemData> itemList) {
		super();
		this.type = type;
		this.itemList = itemList;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<MenuItemData> getItemList() {
		return itemList;
	}

	public void setItemList(List<MenuItemData> itemList) {
		this.itemList = itemList;
	}


}
