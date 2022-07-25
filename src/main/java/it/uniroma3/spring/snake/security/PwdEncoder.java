package it.uniroma3.spring.snake.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PwdEncoder {
	
	private PasswordEncoder passwordEncoder;
	private static PwdEncoder instance;
	
	private PwdEncoder() {
		this.passwordEncoder = new BCryptPasswordEncoder();
	}
	
	public static PwdEncoder getInstance() {
		
		if(instance == null)
			instance = new PwdEncoder();
		
		return instance;
	}
	
	public PasswordEncoder getPasswordEncoder() {
		return this.passwordEncoder;
	}
}
