package com.example.test.repository;


import java.util.Collection;
import com.example.test.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



//@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Long>{
	Collection<? extends Role> findByName(String name);
//	Role findRole(String name);
	
}
