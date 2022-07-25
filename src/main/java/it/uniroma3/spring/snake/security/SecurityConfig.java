package it.uniroma3.spring.snake.security;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig {
 
	@Configuration
	@Order(1)
	public static class APISecurityConfiguration extends WebSecurityConfigurerAdapter {
		
		@Autowired
		protected void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

			auth
			.inMemoryAuthentication()
				.withUser("SnakeReborn")
					.password("Snake123")
					.roles("CLIENT");
			
		}
		
		@Override
	    protected void configure(HttpSecurity http) throws Exception {

	        http
	        .antMatcher("/client/*")
	        	.authorizeRequests()
	        	.anyRequest()
	        	.hasAnyRole("CLIENT")
	            .and()
	        .httpBasic()
	        	.and()
	        .csrf().disable();
	    }
	}
	
	@Configuration
	public static class FormLoginSecurityConfiguration extends WebSecurityConfigurerAdapter {
		
		@Autowired
		private DataSource dataSource;
		
		@Autowired
		protected void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {

			auth
			.jdbcAuthentication()
				.dataSource(dataSource)
				.passwordEncoder(PwdEncoder.getInstance().getPasswordEncoder())
				.usersByUsernameQuery("SELECT username,password,1 FROM users WHERE username=?")
				.authoritiesByUsernameQuery("SELECT username,role FROM users WHERE username=?");
			
		}
		
		@Override
	    protected void configure(HttpSecurity http) throws Exception {

	        http
	        .authorizeRequests()
	            .antMatchers("/profile").authenticated()
	            .anyRequest().permitAll()
	            .and()
	        .formLogin()
	            .loginPage("/login").defaultSuccessUrl("/profile")
	            .usernameParameter("username").passwordParameter("password")
	            .permitAll()
	            .and()
	        .logout()
	        	.logoutUrl("/logout").logoutSuccessUrl("/login")
	        	.permitAll()
	        	.and()
	        .csrf()
	        	.disable();
	    }
	}
}