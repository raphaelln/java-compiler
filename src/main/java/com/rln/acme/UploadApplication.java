package com.rln.acme;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rln.acme.model.Role;
import com.rln.acme.model.UserAccount;
import com.rln.acme.repository.FileUploadRepository;
import com.rln.acme.repository.RoleRepository;
import com.rln.acme.repository.UserAccountRepository;

@SpringBootApplication
public class UploadApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(UploadApplication.class, args);
    }

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Override
    public void run(String... arg0) throws Exception {
        // TODO Auto-generated method stub

        userAccountRepository.deleteAll();
        fileUploadRepository.deleteAll();
        roleRepository.deleteAll();

        Role admin = new Role("admin");
        Role roleUser = new Role("user");

        admin = roleRepository.save(admin);
        roleUser = roleRepository.save(roleUser);

        final List<Role> userAdmin = new ArrayList<Role>();
        userAdmin.add(admin);
        userAdmin.add(roleUser);

        final UserAccount user = new UserAccount();
        user.setEnabled(true);
        user.setFirstname("John");
        user.setLastname("Luke");
        user.setPassword("juka");
        user.setUsername("juka");
        user.setStatus("active");
        user.addRole(admin);
        user.addRole(roleUser);

        userAccountRepository.save(user);

    }

}
