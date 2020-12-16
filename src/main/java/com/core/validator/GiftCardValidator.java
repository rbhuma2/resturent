package com.core.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.core.mongo.data.entity.GiftCard;
import com.core.utils.CommonConstants;
import com.core.utils.DateRoutine;

@Component
public class GiftCardValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return GiftCard.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    	GiftCard giftCard = (GiftCard) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "message", "bad.request.missing.parameter",
                new Object[] { "message" });
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "bad.request.missing.parameter",
                new Object[] { "email" });
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "bad.request.missing.parameter",
                new Object[] { "phoneNumber" });
                
        if (!errors.getAllErrors().isEmpty()) {
            return;
        }
        
        validateEmail(giftCard, errors);
        
        if (giftCard.getAmount() <= 0) {
        	errors.rejectValue("amount", "amount.invalid");
        }

        if (giftCard.getEffectiveDate() == null || giftCard.getEffectiveDate().isEmpty()) {
        	giftCard.setEffectiveDate(DateRoutine.currentDateAsStr());
        }

    }

	private void validateEmail(GiftCard giftCard, Errors errors) {
		boolean isvalid = EmailValidator.getInstance().isValid(giftCard.getEmail());
		if(!isvalid) {
			errors.rejectValue("email", "bad.email.data", new Object[] { "email" },
                    CommonConstants.BLANK_STRING);
		}
		
	}

}
