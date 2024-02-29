package com.picpaysimplificado.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.repositories.TransactionRepository;

@Service
class TransactionServiceTest {

	@Mock
	private UserService userService;
	
	@Mock
	private TransactionRepository transactionRepository;
	
	@Mock
	private NotificationService notificationService;
	
	@Mock
	private AuthorizatinService authorizatinService;
	
	@Autowired
	@InjectMocks
	TransactionService transactionService;
	
	
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	@DisplayName("Should create transaction when everything is OK")
	void createTransactionCase1() throws Exception {
		User sender = new User(1L, "maira", "Souza", "1245642", "vinicyus@gma", "12154545", new BigDecimal(50), UserType.COMMON );
		User receiver = new User(2L, "mai545ra", "S54ouza", "12455445642", "vinic545yus@gma", "12155454545", new BigDecimal(50), UserType.COMMON );
		
		when(userService.findUserById(1L)).thenReturn(sender);
		when(userService.findUserById(2L)).thenReturn(receiver);
		
		when(authorizatinService.authorizeTransaction(any(), any())).thenReturn(true);	
		
		TransactionDTO request = new TransactionDTO(new BigDecimal(50), 1L, 2L);
		transactionService.createTransaction(request);
		
		verify(transactionRepository, times(1)).save(any());
		
		sender.setBalance(new BigDecimal(0));
		verify(userService, times(1)).saveUser(sender);;
		
		receiver.setBalance(new BigDecimal(100));
		verify(userService, times(1)).saveUser(receiver);;
		
		verify(notificationService, times(1)).sendNotification(sender, "Transação realizado com sucesso");
		verify(notificationService, times(1)).sendNotification(receiver, "Transação Recebida com sucesso");
		
		}	
	
	 @Test
	    @DisplayName("Should throw Exception when Transaction is not allowed")
	    void createTransactionCase2() throws Exception {
	        User sender = new User(1L, "Maria", "Souza", "99999999901", "maria@gmail.com", "12345", new BigDecimal(10), UserType.COMMON);
	        User receiver = new User(2L, "Joao", "Souza", "99999999902", "joao@gmail.com", "12345", new BigDecimal(10), UserType.COMMON);

	        when(userService.findUserById(1L)).thenReturn(sender);
	        when(userService.findUserById(2L)).thenReturn(receiver);

	        when(authorizatinService.authorizeTransaction(any(), any())).thenReturn(false);

//	        Exception thrown = Assertions.asserT(Exception.class, () -> {
//	            TransactionDTO request = new TransactionDTO(new BigDecimal(10), 1L, 2L);
//	            transactionService.createTransaction(request);
//	        });
//
//	        Assertions.assertEquals("Transação não autorizada", thrown.getMessage());
	    }
	
	
	
}
