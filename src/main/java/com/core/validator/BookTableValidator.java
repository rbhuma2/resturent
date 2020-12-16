package com.core.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.core.mongo.data.entity.BookTableData;
import com.core.utils.CommonConstants;
import com.core.utils.DateRoutine;

@Component
public class BookTableValidator implements Validator {

    

    @Override
    public boolean supports(Class<?> clazz) {
        return BookTableData.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        BookTableData bookTableData = (BookTableData) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "bad.request.missing.parameter",
                new Object[] { "name" });
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "bad.request.missing.parameter",
                new Object[] { "email" });
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "bad.request.missing.parameter",
                new Object[] { "phoneNumber" });

        if (!errors.getAllErrors().isEmpty()) {
            return;
        }
        
        validateEmail(bookTableData, errors);

        if (bookTableData.getPersons() == null || bookTableData.getPersons().isEmpty()) {
        	bookTableData.setPersons("2");
        }

        if (bookTableData.getDate() == null || bookTableData.getDate().isEmpty()) {
        	bookTableData.setDate(DateRoutine.currentDateAsStr());
        }

    }

	private void validateEmail(BookTableData bookTableData, Errors errors) {
		boolean isvalid = EmailValidator.getInstance().isValid(bookTableData.getEmail());
		if(!isvalid) {
			errors.rejectValue("email", "bad.email.data", new Object[] { "email" },
                    CommonConstants.BLANK_STRING);
		}
		
	}

}
