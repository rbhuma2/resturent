package com.core.validator;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


import com.core.mongo.data.entity.User;
import com.core.mongo.data.repository.UserRepository;
import com.core.utils.CommonConstants;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserRepository userRepository;
	
	@Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

    	User user = (User) target;

    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "bad.request.missing.parameter",
                new Object[] { "name" });
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "bad.request.missing.parameter",
                new Object[] { "email" });
    	ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "bad.request.missing.parameter",
                new Object[] { "phoneNumber" });
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "bad.request.missing.parameter",
                new Object[] { "password" });
               
        
          
        if (!errors.getAllErrors().isEmpty()) {
            return;
        }
        
        validateEmail(user, errors);
        

    }

	private void validateEmail(User user, Errors errors) {
		boolean isvalid = EmailValidator.getInstance().isValid(user.getEmail());
		if(!isvalid) {
			errors.rejectValue("email", "bad.email.data", new Object[] { "email" },
                    CommonConstants.BLANK_STRING);
		}
		
		if(userRepository.findByEmail(user.getEmail()) != null) {
			errors.rejectValue("email", "account.exist", new Object[] { "email" },
                    CommonConstants.BLANK_STRING);
		}
		
	}

}
