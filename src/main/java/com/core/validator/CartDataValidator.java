package com.core.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.core.mongo.data.entity.CartData;
import com.core.utils.DateRoutine;

@Component
public class CartDataValidator implements Validator {

    

    @Override
    public boolean supports(Class<?> clazz) {
        return CartData.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    	CartData cartData = (CartData) target;

    	if (!errors.getAllErrors().isEmpty()) {
            return;
        }
        
        if (!errors.getAllErrors().isEmpty()) {
            return;
        }
        
        if (cartData.getExpirationDate() == null || cartData.getExpirationDate().isEmpty()) {
        	cartData.setExpirationDate(DateRoutine.dateTimeAsYYYYMMDDHHhhmmssSSSString(DateRoutine.currentTimestamp()));
        }

    }

	

}
