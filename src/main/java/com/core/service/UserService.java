package com.core.service;

import java.util.List;

import com.core.mongo.data.entity.User;

public interface UserService {

    public User findUserData(String id);

    public void resetUserData(User user);

    public User saveUserData(User user, List<String> emailList);
    
    public User validateUserData(User user);

    

}
