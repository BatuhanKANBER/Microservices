package com.user.user.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.user.user.models.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

}
