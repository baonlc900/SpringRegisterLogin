package com.example.test.service;

import com.example.test.model.Role;
import com.example.test.model.User;
import com.example.test.repository.RoleRepository;
import com.example.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));       
        user.setRoles(new HashSet<Role>(roleRepository.findByName("ROLE_USER")));
//        Role userRole = roleRepository.findRole("ROLE_USER");
//        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
    }

//    private Set<Role> hash(Role findByName) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
