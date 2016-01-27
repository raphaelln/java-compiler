package com.rln.acme.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rln.acme.model.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

}