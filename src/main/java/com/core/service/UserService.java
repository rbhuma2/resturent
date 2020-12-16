package com.core.service;

import com.core.mongo.data.entity.User;

public interface UserService {

    public User findUserData(String id);

    public void resetUserData(User user);

    public User saveUserData(User user);
    
    public User validateUserData(User user);

    

}
