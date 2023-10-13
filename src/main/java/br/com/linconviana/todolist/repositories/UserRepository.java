package br.com.linconviana.todolist.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.linconviana.todolist.entities.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Long>{

	User findByUserName(String userName);
}
