package com.rln.acme.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rln.acme.model.UserAccount;
import com.rln.acme.repository.UserAccountRepository;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public List<UserAccount> findByPasswordAndUsername(String password, String username) {
        return userAccountRepository.findByPasswordAndUsername(password, username);
    }

    @Override
    public UserAccount findByUsername(String username) {
        return userAccountRepository.findByUsername(username);
    }

    @Override
    public List<UserAccount> findByUsernameAndPassword(String username, String password) {
        return userAccountRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public List<UserAccount> findByUsernameAndPasswordQuery(String username, String password) {
        return userAccountRepository.findByUsernameAndPasswordQuery(username, password);
    }

    @Override
    public List<UserAccount> findByUsernameLike(String username) {
        return userAccountRepository.findByUsernameLike(username);
    }

}
