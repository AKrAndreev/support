package org.isiktir.isupport.repositories;

import org.isiktir.isupport.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,String> {
    Role findByAuthority(String role);
}
