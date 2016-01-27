package com.rln.acme.service;

import java.util.List;

import com.rln.acme.model.UserAccount;

public interface UserAccountService {

    List<UserAccount> findByPasswordAndUsername(final String password, final String username);

    UserAccount findByUsername(final String username);

    List<UserAccount> findByUsernameAndPassword(final String username, final String password);

    List<UserAccount> findByUsernameAndPasswordQuery(final String username, final String password);

    List<UserAccount> findByUsernameLike(final String username);

}
