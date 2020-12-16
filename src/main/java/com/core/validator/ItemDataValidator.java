package com.core.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.core.exception.application.InvalidDataException;
import com.core.mongo.data.embedded.Item;
import com.core.mongo.data.entity.BookTableData;
import com.core.mongo.data.entity.CartData;
import com.core.mongo.data.entity.ItemData;
import com.core.utils.CommonConstants;
import com.core.utils.DateRoutine;

@Component
public class ItemDataValidator implements Validator {

    

    @Override
    public boolean supports(Class<?> clazz) {
        return ItemData.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    	ItemData itemData = (ItemData) target;

    	/*for(Item item : cartData.getItemList()) {
        	if(item.getName() == null || item.getName().isEmpty()) {
        		throw new InvalidDataException("bad.request.missing.parameter", new Object[] { "name" });
        		
        	}
    		
            
        }*/
    	

        if (!errors.getAllErrors().isEmpty()) {
            return;
        }
        
        if (!errors.getAllErrors().isEmpty()) {
            return;
        }
        
        /*if (cartData.getExpirationDate() == null || cartData.getExpirationDate().isEmpty()) {
        	cartData.setExpirationDate(DateRoutine.dateTimeAsYYYYMMDDString(DateRoutine.defaultDateTime()));
        }*/

    }

	

}
