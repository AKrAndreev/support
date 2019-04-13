package org.isiktir.isupport.service;

import org.isiktir.isupport.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserServiceModel register(UserServiceModel userServiceModel);
    UserServiceModel findByName(String name);
}
