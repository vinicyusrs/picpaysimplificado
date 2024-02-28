package com.picpaysimplificado.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.repositories.UserRepository;
import com.picpaysimplificado.service.exceptions.ExceptionsService;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public void validateTransaction(User sender, BigDecimal amount) throws Exception {
		if (sender.getUserType() == UserType.MERCHANT) {
			throw new Exception(ExceptionsService.MERCHAN_NAO_AUTORIZADO.getDescricao());		
		}
		
		if(sender.getBalance().compareTo(amount) < 0){
			throw new Exception(ExceptionsService.SALVO_INSUFICIENTE.getDescricao());
		}
	}
	
	public User findUserById(Long id) throws Exception {
		return this.userRepository.findUserById(id).orElseThrow(() -> new Exception(ExceptionsService.USER_NAO_ENCONTRADO.getDescricao()));
	}
	
	public void saveUser(User user) {
		this.userRepository.save(user);
	}
	
}
