package br.com.linconviana.todolist.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.linconviana.todolist.entities.Task;

@Repository
public interface TaskRepository  extends JpaRepository<Task, Long>{

	Task findByTitle(String title);

	List<Task> findByUserId(Long id);
}
