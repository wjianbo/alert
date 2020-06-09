package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.User;
import com.example.demo.UserRepository;

@Controller
public class RouterController {
	
    @Autowired
    private UserRepository userRepository;
	
	@RequestMapping({"/index"})
	public String index() {
		return "index";
	}
	
	@RequestMapping("login")
	public String login() {
		return "user/login";
	}
	
	@RequestMapping("signup")
	public String signup() {
		return "user/signup";
	}
	
    @RequestMapping("/addUser")
    public String add(RedirectAttributes attributes, User user, Model model) {
    	
    	if (userRepository.findByName(user.getName()).size() > 0) {
    		System.out.println(userRepository.findByName(user.getName()).get(0).toString());
    		attributes.addFlashAttribute("message", "Username already exists. Please try a different one.");
    		return "redirect:/signup";
    	} else if (userRepository.findByEmail(user.getEmail()).size() > 0) {
    		attributes.addFlashAttribute("message", "Email already signed up. Please try a different one");
    		return "redirect:/signup";
    	} else {
	    	user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
	    	user.setEnabled(true);
	    	System.out.println(user.toString());
	    	userRepository.save(user);
	    	model.addAttribute("message", "Signed up successfully. Please log in to use.");
	        return "user/login";
    	}
    }
    
	
	@RequestMapping("/{level}/{id}")
	public String level(@PathVariable("level") String level, @PathVariable("id") int id) {
		return String.format("views/%s/%d", level, id);
	}
}
