package com.core.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.core.model.ChargeRequest.Currency;
import com.core.model.CheckOut;

@Component
public class CheckOutValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CheckOut.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    	CheckOut checkOut = (CheckOut) target;

    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amount", "bad.request.missing.parameter",
                new Object[] { "amount" });
    	  
        if (!errors.getAllErrors().isEmpty()) {
            return;
        }
        
        formatCurrency(checkOut);
        

    }

	private void formatCurrency(CheckOut checkOut) {
		if(checkOut.getCurrency() == null) {
			checkOut.setCurrency(Currency.EUR);
		}
		
	}

	
}
