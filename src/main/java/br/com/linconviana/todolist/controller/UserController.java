package br.com.linconviana.todolist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.linconviana.todolist.entities.User;
import br.com.linconviana.todolist.repositories.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public List<User> gelAll() {
		return userRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity create(@RequestBody User user) {
		
		User findUser = userRepository.findByUserName(user.getUserName());
		if(findUser != null) {
			System.out.println("ja tem");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("já existe um usuário cadastro com este e-mail");
		}
		
		var passwordHash = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
		user.setPassword(passwordHash);
		
		user = userRepository.save(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
}
