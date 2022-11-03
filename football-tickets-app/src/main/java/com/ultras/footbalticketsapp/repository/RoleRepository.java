package com.ultras.footbalticketsapp.repository;

import com.ultras.footbalticketsapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    //TODO remove class since user uses enum
    Role findByName(String name);
}
