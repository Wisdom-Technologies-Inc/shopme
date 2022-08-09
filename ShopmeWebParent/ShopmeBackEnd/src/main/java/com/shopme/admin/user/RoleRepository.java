package com.shopme.admin.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopme.common.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
