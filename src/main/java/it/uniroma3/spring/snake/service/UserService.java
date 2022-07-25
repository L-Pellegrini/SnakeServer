package it.uniroma3.spring.snake.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import it.uniroma3.spring.snake.model.Credentials;
import it.uniroma3.spring.snake.model.User;
import it.uniroma3.spring.snake.repository.UserRepository;
import java.util.Optional;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	public Iterable<User> findAll() {
		return this.userRepository.findAll();
	}
	
	@Transactional
	public void add(User user) {
		this.userRepository.save(user);
	}
	
	public User findbyId(Long id) {
		Optional<User> optionalUser = this.userRepository.findById(id);
		if(optionalUser.isPresent()) {
			return optionalUser.get();
		}
		else {
			return null;
		}
	}
	
	public User findbyUsername(String username) {
		return this.userRepository.findByUsername(username);
	}
	
	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}
	
	public User findAndCheckCredentials(Credentials providedCredentials) {
		User user = null;
		
		String providedUsername = providedCredentials.getUsername();
		String providedPassword = providedCredentials.getPassword();
		User tempUser = this.findbyUsername(providedUsername);
		if(tempUser != null) {
			if(tempUser.checkPassword(providedPassword)) {
				user = tempUser;
			}
		}
		
		return user;
	}
	
	public boolean existsByUsername(String username) {
		User user = this.userRepository.findByUsername(username);
		
		if(user != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean existsByEmail(String email) {
		User user = this.userRepository.findByEmail(email);
		
		if(user != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public void delete(User user) {
		this.userRepository.delete(user);
	}
}
