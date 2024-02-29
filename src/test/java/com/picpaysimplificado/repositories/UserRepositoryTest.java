package com.picpaysimplificado.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.picpaysimplificado.domain.user.User;
import com.picpaysimplificado.domain.user.UserType;
import com.picpaysimplificado.dtos.UserDTO;
import com.picpaysimplificado.repositories.UserRepository;

import jakarta.persistence.EntityManager;


@DataJpaTest
@ActiveProfiles("test")
class UserRepositoryTest{

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	EntityManager entityManager;
	
	@Test
	@DisplayName("Should get User successfully from DB")
	void findUserByDocumentCase1() {
		String document = "99999558555";
		UserDTO data = new UserDTO("Fernada", "rodrigues", document, new BigDecimal(10) , "email@email.com", "44444", UserType.COMMON );
		this.createUser(data);
		
		Optional<User> result = this.userRepository.findUserByDocument(document);
		
		assertThat(result.isPresent()).isTrue();
		
	}
	
	@Test
	@DisplayName("Should get User successfully from DB")
	void findUserByDocumentCase2failure() {
		String document = "99999558555";
	
		Optional<User> result = this.userRepository.findUserByDocument(document);
		
		assertThat(result.isEmpty()).isTrue();
		
	}
	
	
	private User createUser(UserDTO data) {
		User newUser = new User(data);
		this.entityManager.persist(newUser);
		
		return newUser;
	}
	
}
