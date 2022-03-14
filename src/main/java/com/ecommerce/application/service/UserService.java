package com.ecommerce.application.service;

import com.ecommerce.application.model.Category;
import com.ecommerce.application.model.User;
import com.ecommerce.application.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser (User user){
        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        User userExists = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User does not exist"));
        userRepository.deleteById(id);
    }
}
