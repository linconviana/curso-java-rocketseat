package br.com.linconviana.todolist.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.linconviana.todolist.entities.Task;
import br.com.linconviana.todolist.entities.User;
import br.com.linconviana.todolist.repositories.TaskRepository;
import br.com.linconviana.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/tasks")
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;
	
	@GetMapping
	public List<Task> gelAll(HttpServletRequest request) {
		User user = (User) request.getAttribute("user");
		var tasks = taskRepository.findByUserId(user.getId());
		return tasks;
	}
	
	@PostMapping
	public ResponseEntity create(@RequestBody Task task, HttpServletRequest request) {
		
		User user = (User) request.getAttribute("user");
		task.setUser(user);
		
		var currentDate = LocalDateTime.now();
		if(currentDate.isAfter(task.getStartAt()) || currentDate.isAfter(task.getEndAt())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data inicio / data fim deve ser maior que a data atual ".concat(currentDate.toString()));
		}
		
		if(task.getStartAt().isAfter(task.getEndAt())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data inicio não pode ser maior que a data de fim");
		}
		
		task = taskRepository.save(task);
		return ResponseEntity.status(HttpStatus.CREATED).body(task);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity create(@RequestBody Task task, @PathVariable Long id, HttpServletRequest request) {
		
		User user = (User) request.getAttribute("user");
		task.setUser(user);
		
		Optional<Task>taskDb = taskRepository.findById(id);
		
		if(!taskDb.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esta tarefa não existe");
		}
		
		if(!task.getUser().getId().equals(taskDb.get().getUser().getId())) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não tem permissão para atualizar esta tarefa");
		}
		
		Utils.copyNonNullProperties(task, taskDb);
		
		task = taskRepository.save(taskDb.get());
		return ResponseEntity.status(HttpStatus.OK).body(task);
	}
}
