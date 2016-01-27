package com.rln.acme.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.rln.acme.model.UserAccount;

@Repository
public interface UserAccountRepository extends MongoRepository<UserAccount, String> {

    List<UserAccount> findByPasswordAndUsername(final String password, final String username);

    UserAccount findByUsername(final String username);

    List<UserAccount> findByUsernameAndPassword(final String username, final String password);

    @Query("{ 'username' : ?0, 'password' : ?1 }")
    List<UserAccount> findByUsernameAndPasswordQuery(final String username, final String password);

    List<UserAccount> findByUsernameLike(final String username);
}