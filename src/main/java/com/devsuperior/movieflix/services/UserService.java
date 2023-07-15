package com.devsuperior.movieflix.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService{
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthService authService;   
	
	@Transactional(readOnly = true)
	public UserDTO findByProfile(String profile) {
		User user = authService.validateSelfLoggerdUser(profile);
		return new UserDTO(user);
	}
	
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		User user = authService.validateSelfLoggerdUser2(id);
		return new UserDTO(user);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			logger.error("User not found! " + username);
			throw new UsernameNotFoundException("Email not found!");
		}
		logger.info("User found" + username);
		return user;
	}
}
