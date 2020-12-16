package com.core.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.core.mongo.data.entity.FeedBack;
import com.core.utils.CommonConstants;
import com.core.utils.DateRoutine;

@Component
public class FeedBackValidator implements Validator {

    

    @Override
    public boolean supports(Class<?> clazz) {
        return FeedBack.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    	FeedBack feedBack = (FeedBack) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "bad.request.missing.parameter",
                new Object[] { "firstName" });
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "bad.request.missing.parameter",
                new Object[] { "lastName" });
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "bad.request.missing.parameter",
                new Object[] { "email" });
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "bad.request.missing.parameter",
                new Object[] { "phoneNumber" });
        
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "bad.request.missing.parameter",
                new Object[] { "description" });

        if (!errors.getAllErrors().isEmpty()) {
            return;
        }
        
        validateEmail(feedBack, errors);

        if (feedBack.getDate() == null || feedBack.getDate().isEmpty()) {
        	feedBack.setDate(DateRoutine.currentDateAsStr());
        }

    }

	private void validateEmail(FeedBack feedBack, Errors errors) {
		boolean isvalid = EmailValidator.getInstance().isValid(feedBack.getEmail());
		if(!isvalid) {
			errors.rejectValue("email", "bad.email.data", new Object[] { "email" },
                    CommonConstants.BLANK_STRING);
		}
		
	}

}
