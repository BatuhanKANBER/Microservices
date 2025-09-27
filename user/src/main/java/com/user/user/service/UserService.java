package com.user.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.user.user.model.User;
import com.user.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User is not found"));
    }

    public User updateUser(User user, String id) {
        User updatedUser = getUser(id);
        updatedUser.setName(user.getName());
        updatedUser.setSurname(user.getSurname());
        return userRepository.save(updatedUser);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
