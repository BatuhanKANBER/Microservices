package com.user.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.user.user.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
