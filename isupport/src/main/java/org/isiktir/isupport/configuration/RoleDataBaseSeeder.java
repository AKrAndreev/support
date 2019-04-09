package org.isiktir.isupport.configuration;

import org.isiktir.isupport.domain.entities.Role;
import org.isiktir.isupport.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RoleDataBaseSeeder {

    private final RoleRepository repository;

    @Autowired
    public RoleDataBaseSeeder(RoleRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    public void seed(){

        if (this.repository.findAll().isEmpty()){
            Role adminRole = new Role();
            adminRole.setAuthority("ROLE_ADMIN");

            Role moderatorRole = new Role();
            moderatorRole.setAuthority("ROLE_MODERATOR");

            Role userSupporterRole = new Role();
            userSupporterRole.setAuthority("ROLE_SUPPORTER");

            Role userSupportedRole = new Role();
            userSupportedRole.setAuthority("ROLE_SUPPORTED");

            this.repository.save(adminRole);
            this.repository.save(moderatorRole);
            this.repository.save(userSupporterRole);
            this.repository.save(userSupportedRole);
        }
    }
}
