package com.devsuperior.movieflix.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.entities.Role;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.UserRepository;
import com.devsuperior.movieflix.services.exceptions.ForbiddenException;
import com.devsuperior.movieflix.services.exceptions.UnauthorizedException;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Transactional(readOnly = true)
	public User authenticated() {
		try {
			String userName = SecurityContextHolder.getContext().getAuthentication().getName();
			return userRepository.findByEmail(userName);
		} catch (Exception e) {
			throw new UnauthorizedException("Invalid User!");
		}
	}

	public User validateSelfLoggerdUser(String profile) {
		User user = authenticated();
		for (Role role : user.getRoles()) {
			if (!role.getAuthority().equals(profile) && !user.hasRole("VISITOR")) {
				throw new ForbiddenException("Access Denied");
			} if (!role.getAuthority().equals(profile) && !user.hasRole("MEMBER")) {
					throw new ForbiddenException("Access Denied");
			}
		}
		return user;
	}
	
	public User validateSelfLoggerdUser2(Long id) {
		User user = authenticated();
			if (!user.getId().equals(id) && !user.hasRole("VISITOR")) {
				throw new ForbiddenException("Access Denied");
			} if (!user.getId().equals(id) && !user.hasRole("MEMBER")) {
					throw new ForbiddenException("Access Denied");
			}
		return user;
	}
}
