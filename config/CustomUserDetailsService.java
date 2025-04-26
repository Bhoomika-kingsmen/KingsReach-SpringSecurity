package com.kingsmen.kingsreachdev.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kingsmen.kingsreachdev.entity.Employee;
import com.kingsmen.kingsreachdev.repo.EmployeeRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	    @Autowired
	    private EmployeeRepo employeeRepo;

	    @Override
	    public UserDetails loadUserByUsername(String officialEmail) throws UsernameNotFoundException {
	    	 Employee employee = employeeRepo.findByOfficialEmail(officialEmail)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	    	 return new User(
	    		        employee.getOfficialEmail(),
	    		        employee.getPassword(),
	    		        Collections.singleton(new SimpleGrantedAuthority("ROLE_" + employee.getRole().toString())) 
	    		    );
	    }

	}

