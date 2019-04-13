package org.isiktir.isupport.service;

import org.isiktir.isupport.domain.entities.Role;
import org.isiktir.isupport.domain.entities.User;
import org.isiktir.isupport.domain.models.service.UserServiceModel;
import org.isiktir.isupport.repositories.RoleRepository;
import org.isiktir.isupport.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final ModelMapper mapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository repository, ModelMapper mapper, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        User user = mapper.map(userServiceModel,User.class);
        user.setPassword(bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        if (repository.count()==0){
            user.setAuthorities(new HashSet<>(roleRepository.findAll()));
        }else {
            if (userServiceModel.isNeedSupport()){

                user.setAuthorities(giveRoles("ROLE_SUPPORTED"));
            }else {
                Set<Role> roles = new HashSet<>();

                user.setAuthorities(giveRoles("ROLE_SUPPORTER"));
            }
        }
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        user.setEnabled(true);
        return mapper.map(repository.save(user), UserServiceModel.class);
    }

    @Override
    public UserServiceModel findByName(String name) {

        User user = repository.findByUsername(name).orElseThrow(()->new UsernameNotFoundException("User not found!"));
        UserServiceModel userServiceModel = mapper
                .map(user,UserServiceModel.class);

        return  userServiceModel;
    }

    private Set<Role> giveRoles(String role) {
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByAuthority(role));
        return roles;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return repository
                .findByUsername(s)
                .orElseThrow(()->new UsernameNotFoundException("User not found!"));
    }
}
