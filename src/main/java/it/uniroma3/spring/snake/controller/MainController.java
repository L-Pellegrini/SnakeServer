package it.uniroma3.spring.snake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import it.uniroma3.spring.snake.model.User;
import it.uniroma3.spring.snake.service.UserService;

@Controller
public class MainController {

	  @Autowired
	  private UserService userService;
	
	  @RequestMapping("")
	  public String index() {
		  return "login";
	  }
	  
	  @GetMapping("login")
	  public String login() {
		  return "login";
	  }
	  
	  @RequestMapping("register")
	  public String register(Model model) {
		  model.addAttribute("user", new User());
		  return "registrazione";
	  }
	  
	  @RequestMapping("backToRegister")
	  public String backToRegister(HttpSession httpSession, Model model) {
		  model.addAttribute(httpSession.getAttribute("user"));
		  return "registrazione";
	  }
	  
	  @RequestMapping("confirm")
	  public String confimUser(@Valid @ModelAttribute User user, BindingResult bindingResult, HttpSession httpSession) {
		  
		  if(bindingResult.hasErrors()) {
			  return "registrazione";
		  } else {
			  httpSession.setAttribute("user", user);
			  return "confermaDati";
		  }
	  }
}
