package com.core.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.core.exception.application.DataNotFoundException;
import com.core.mongo.data.entity.User;
import com.core.mongo.data.repository.UserRepository;
import com.core.service.UserService;
import com.core.utils.PasswordUtils;

@Service
public class UserServiceImpl implements UserService{

	
	@Value("${salt.value}")
    private String saltValue;
	
	@Value("${admin.email}")
    private String email;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findUserData(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetUserData(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User saveUserData(User user, List<String> emailList) {
		
		if(!emailList.isEmpty() ) {
			if(emailList.get(0).equalsIgnoreCase(email)) {
				user.setAdmin(true);
			}else {
				User adminUser = userRepository.findByEmail(user.getEmail());
				if(adminUser != null && adminUser.isAdmin()) {
					user.setAdmin(true);
				}
			}
		}
		
		String password = PasswordUtils.generateSecurePassword(user.getPassword(), saltValue);
		user.setPassword(password);
		return userRepository.save(user);
	}

	@Override
	public User validateUserData(User user) {
		
		User userData = null;
		if(user.getEmail().equalsIgnoreCase(email)) {
			userData = new User();
			userData.setEmail(email);
			userData.setAdmin(true);
			return userData;
			
		}
		if(user.getEmail() == null || user.getPassword() == null
				|| user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
			throw new DataNotFoundException("bad.email.validation");
		}
		String password = PasswordUtils.generateSecurePassword(user.getPassword(), saltValue);
		user.setPassword(password);
		
		if(user.getEmail() != null) {
			userData = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
		}else {
			userData = userRepository.findByPhoneNumberAndPassword(user.getPhoneNumber(), user.getPassword());
		}
		
		if(userData == null) {
			throw new DataNotFoundException("no.user.password.exists");
		}
		
		return userData;
	}

}
