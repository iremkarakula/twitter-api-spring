package com.project.twitter.service.Impl;

import com.project.twitter.entity.Role;
import com.project.twitter.repository.RoleRepository;
import com.project.twitter.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final String ROLE_ADMIN = "ADMIN";
    private final String ROLE_USER = "USER";

    private RoleRepository roleRepository;

    @Override
    public void addAdminRole(List<Role> roleList) {

       Optional<Role> roleAdmin = roleRepository.findByAuthority(ROLE_ADMIN);

       if(roleAdmin.isPresent()){

            roleList.add(roleAdmin.get());

       } else {

           Role role = new Role();
           role.setAuthority(ROLE_ADMIN);
           roleList.add(roleRepository.save(role));

       }
    }

    @Override
    public void addUserRole(List<Role> roleList) {

        Optional<Role> roleUser = roleRepository.findByAuthority(ROLE_USER);

        if(roleUser.isPresent()){

            roleList.add(roleUser.get());

        } else {

            Role role = new Role();
            role.setAuthority(ROLE_USER);
            roleList.add(roleRepository.save(role));

        }
    }
}
