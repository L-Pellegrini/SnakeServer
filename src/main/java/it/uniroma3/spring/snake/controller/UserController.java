package it.uniroma3.spring.snake.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.uniroma3.spring.snake.service.MatchService;
import it.uniroma3.spring.snake.service.UserService;
import it.uniroma3.spring.snake.model.User;
import it.uniroma3.spring.snake.repository.RoleRepository;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private MatchService matchService;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@PostMapping("user")
	public String registerUser(HttpSession httpSession, Model model) {
		
		User user = (User)httpSession.getAttribute("user");
		
		user.encodePassword();
		user.setRole(roleRepository.findByName("USER"));
		
		userService.add(user);
		
		model.addAttribute(user);
		return "login";
	}
	
	@RequestMapping("profile")
	public String viewUserPage(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findbyUsername(username);
		
		model.addAttribute("user", user);
		model.addAttribute("topTen", matchService.findTopTen());
		model.addAttribute("bestOfUser", matchService.findBestMatchOf(user));
		
		return "profilo";
	}
	
	@GetMapping("delete")
	public String confirmDeleteProfile(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findbyUsername(username);
		model.addAttribute("user", user);
		return "confermaEliminazioneProfilo";
	}
	
	@PostMapping("delete")
	public String deleteProfile() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findbyUsername(username);
		userService.delete(user);
		return "login";
	}
	
	@GetMapping("edit")
	public String editProfilePage(Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = userService.findbyUsername(username);
		model.addAttribute("user", user);
		return "modificaProfilo";
	}
	
	@PostMapping("edit")
	public String editProfile(@Valid @ModelAttribute User user,	BindingResult bindingResult, Model model) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User oldUser = userService.findbyUsername(username);
		
		if(bindingResult.hasErrors()) {
		  
			boolean usernameOk = false;
			boolean emailOk = false;
			boolean passwordOk = true;
			
		
			if(bindingResult.hasFieldErrors("username")) {
				if(bindingResult.getFieldError("username").getRejectedValue().equals(oldUser.getUsername())) {
					usernameOk = true;
				}
			}
		  
			if(bindingResult.hasFieldErrors("email")) {
				if(bindingResult.getFieldError("email").getRejectedValue().equals(oldUser.getEmail())) {
					emailOk = true;
				}
			}
			
			if(bindingResult.hasFieldErrors("password")) {
				passwordOk = false;
			}
			  
			if(usernameOk && emailOk && passwordOk) {
				userService.add(user);
				return "profilo";
			} else {
				return "modificaProfilo";
			}
	  } else {
		  user.encodePassword();
		  userService.add(user);
		  return "profilo";
	  }
  }
}
