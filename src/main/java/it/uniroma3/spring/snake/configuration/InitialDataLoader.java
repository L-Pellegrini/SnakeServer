package it.uniroma3.spring.snake.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.spring.snake.model.Match;
import it.uniroma3.spring.snake.model.Role;
import it.uniroma3.spring.snake.model.User;
import it.uniroma3.spring.snake.repository.RoleRepository;
import it.uniroma3.spring.snake.service.MatchService;
import it.uniroma3.spring.snake.service.UserService;

@Component
public class InitialDataLoader implements
  ApplicationListener<ContextRefreshedEvent> {
 
    boolean alreadySetup = false;
  
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private MatchService matchService;
  
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
  
        if (alreadySetup)
            return;      
        createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("CLIENT");
        createRoleIfNotFound("USER");
        
        alreadySetup = true;
    }
 
    @Transactional
    private Role createRoleIfNotFound(String name) {
  
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }
}