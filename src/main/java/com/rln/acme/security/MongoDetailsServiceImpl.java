package com.rln.acme.security;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rln.acme.model.UserAccount;
import com.rln.acme.service.UserAccountService;

@Service
public class MongoDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(MongoDBAuthenticationProvider.class);

    @Autowired
    private UserAccountService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final UserAccount user = userService.findByUsername(username);
        if (user == null) {
            logger.warn("Username {}: username not found", username);
            throw new BadCredentialsException("Invalid Username");
        }

        final List<GrantedAuthority> auths;
        if (CollectionUtils.isNotEmpty(user.getRoles())) {
            auths = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles().stream().map(r -> r.getId()).collect(Collectors.joining(",")));
        } else {
            auths = AuthorityUtils.NO_AUTHORITIES;
        }

        return new User(username, user.getPassword(), user.getEnabled(), // enabled
            true, // account not expired
            true, // credentials not expired
            true, // account not locked
            auths);
    }

}
