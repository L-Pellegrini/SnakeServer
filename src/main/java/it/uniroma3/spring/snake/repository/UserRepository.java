package it.uniroma3.spring.snake.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.spring.snake.model.User;

public interface UserRepository extends CrudRepository<User, Long> {

	public User findByUsername(String username);
	
	public User findByEmail(String email);
}
