package com.devsuperior.dsCommerce.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devsuperior.dsCommerce.entities.Role;
import com.devsuperior.dsCommerce.entities.User;
import com.devsuperior.dsCommerce.projections.UserDetailsProjection;
import com.devsuperior.dsCommerce.repositories.UserRepository;


@Service
public class UserService implements UserDetailsService{

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> userList = repository.searchUserAndRolesByEmail(username);
        if(userList.size() == 0){
            throw new UsernameNotFoundException("User not found");
        }
        User user = new User();
        user.setEmail(username);
        user.setPassword(userList.get(0).getPassword());
        for (UserDetailsProjection details : userList) {
            user.addRole(new Role(details.getRoleId(), details.getAuthority()));
        }

        return user;
    }
}