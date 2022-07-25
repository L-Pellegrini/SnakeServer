package it.uniroma3.spring.snake.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.spring.snake.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long>{

	public Role findByName(String name);
}
