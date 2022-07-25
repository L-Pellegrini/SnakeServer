package it.uniroma3.spring.snake.controller;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.uniroma3.spring.snake.model.Credentials;
import it.uniroma3.spring.snake.model.Match;
import it.uniroma3.spring.snake.model.User;
import it.uniroma3.spring.snake.service.MatchService;
import it.uniroma3.spring.snake.service.UserService;

@RestController
public class APIController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MatchService matchService;

	@RequestMapping("client/logUser")
	public User logUser(@RequestBody Credentials credentials,
									HttpSession httpSession) {
		User user = userService.findAndCheckCredentials(credentials);

		if(user != null) {
			httpSession.setAttribute("user", user);
		}
		
		return user;
	}
	
	@RequestMapping("client/addMatch")
	public User addMatch(@RequestBody Match match, 
									HttpSession httpSession) {
	
		User user = (User)httpSession.getAttribute("user");

		user.addMatchAndUpdateStats(match);
		
		matchService.add(match);
		userService.add(user);
		
		return user;
	}
}
