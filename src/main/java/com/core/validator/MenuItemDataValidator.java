package com.core.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.core.mongo.data.entity.MenuItemData;

@Component
public class MenuItemDataValidator implements Validator {

    

    @Override
    public boolean supports(Class<?> clazz) {
        return MenuItemData.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    	MenuItemData itemData = (MenuItemData) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "bad.request.missing.parameter",
                new Object[] { "name" });
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "category", "bad.request.missing.parameter",
                new Object[] { "category" });
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "type", "bad.request.missing.parameter",
                new Object[] { "type" });
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "bad.request.missing.parameter",
                new Object[] { "description" });

        
        if (itemData.getAmount() <= 0) {
        	errors.rejectValue("amount", "amount.invalid");
        }
        

    }

}
