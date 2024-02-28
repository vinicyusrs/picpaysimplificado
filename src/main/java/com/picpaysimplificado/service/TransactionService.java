package com.picpaysimplificado.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.picpaysimplificado.domain.transaction.Transaction;
import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.dtos.TransactionDTO;
import com.picpaysimplificado.repositories.TransactionRepository;
import com.picpaysimplificado.service.exceptions.ExceptionsService;

@Service
public class TransactionService {

	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public Transaction createTransaction(TransactionDTO transaction) throws Exception {
		User sender = this.userService.findUserById(transaction.senderId());
		User receiver = this.userService.findUserById(transaction.receiverId());
		
		userService.validateTransaction(sender, transaction.value());
		
		boolean isAutorized = this.authorizeTransaction(sender, transaction.value());
	//	if(this.authorizeTransaction(sender, transaction.value())) {
//		if(isAutorized){
//			throw new Exception(ExceptionsService.TRANSACAO_NAO_AUTORIZADA.getDescricao());
//		}
//		
		Transaction newTransaction = new Transaction();
		newTransaction.setAmount(transaction.value());
		newTransaction.setSender(sender);
		newTransaction.setReceiver(receiver);
		newTransaction.setTimestamp(LocalDateTime.now());
		
		sender.setBalance(sender.getBalance().subtract(transaction.value()));
		receiver.setBalance(receiver.getBalance().add(transaction.value()));
		
		this.transactionRepository.save(newTransaction);
		this.userService.saveUser(sender);
		this.userService.saveUser(receiver);
		
		this.notificationService.sendNotification(sender, ExceptionsService.TRANSACAO_REALIZADA_SUCESSO.getDescricao());
		
		this.notificationService.sendNotification(sender, ExceptionsService.TRANSACAO_RECEBIDA_SUCESSO.getDescricao());
		return newTransaction;
	}
	
	public boolean authorizeTransaction(User sender, BigDecimal value) {
		ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);
		
		// if(authorizationResponse.getStatusCode() == HttpStatus.OK && authorizationResponse.getBody().get("message") == "Autorizado" ) {
		if(authorizationResponse.getStatusCode() == HttpStatus.OK) {
			String message = (String)authorizationResponse.getBody().get("message");
			return "Autorizado".equalsIgnoreCase(message);
		} else return false;
		
		
	}
	
	
}
