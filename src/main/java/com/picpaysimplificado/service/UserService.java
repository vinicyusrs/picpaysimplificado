package com.picpaysimplificado.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public void validateTransaction(User sender, BigDecimal amount) {
		if (sender.getUserType() != UserType.COMMON) {
			
		}
	}
}
