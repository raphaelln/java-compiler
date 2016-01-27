/*
 * *
 *  * Copyright (c) 2015 Ivan Hristov
 *  * <p/>
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  * <p/>
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  * <p/>
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.rln.acme.security;


import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.rln.acme.model.UserAccount;
import com.rln.acme.service.UserAccountService;

@Service
public class MongoDBAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(MongoDBAuthenticationProvider.class);


    @Autowired
    private UserAccountService userService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        final String password = (String) authentication.getCredentials();
        if (!StringUtils.isNotBlank(password)) {
            logger.warn("User {}: no password provided", username);
            throw new BadCredentialsException("Please enter password");
        }

        final UserAccount user = userService.findByUsername(username);
        if (user == null) {
            logger.warn("Username {}, password {}: username and password not found", username, password);
            throw new BadCredentialsException("Invalid Username/Password");
        }

        final List<GrantedAuthority> auths;
        if (CollectionUtils.isNotEmpty(user.getRoles())) {
            auths = AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles().stream().map(r -> r.getId()).collect(Collectors.joining(",")));
        }else {
            auths = AuthorityUtils.NO_AUTHORITIES;
        }

        return new User(username, password, user.getEnabled(), // enabled
            true, // account not expired
            true, // credentials not expired
            true, // account not locked
            auths);
    }
}