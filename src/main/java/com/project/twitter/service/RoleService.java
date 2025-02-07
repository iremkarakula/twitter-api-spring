package com.project.twitter.service;

import com.project.twitter.entity.Role;

import java.util.List;

public interface RoleService {

    void addAdminRole(List<Role> roleList);

    void addUserRole(List<Role> roleList);
}
