package br.com.linconviana.todolist.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/// :: deploy -> https://render.com/
@Entity
@Table(name = "tb_task")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String description;
	
	@Column(length = 64)
	private String title;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	private String priority;
	
	@CreationTimestamp
	private LocalDateTime createAt;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public Task() {
		
	}

	public Task(Long id, String description, String title, LocalDateTime startAt, LocalDateTime endAt, String priority,
			LocalDateTime createAt, User user) {
		this.id = id;
		this.description = description;
		this.title = title;
		this.startAt = startAt;
		this.endAt = endAt;
		this.priority = priority;
		this.createAt = createAt;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws Exception {
		
		if(title.length() > 64) {
			throw new Exception("O campo title deve ter no maximo 64 caracteres!");
		}
		this.title = title;
	}

	public LocalDateTime getStartAt() {
		return startAt;
	}

	public void setStartAt(LocalDateTime startAt) {
		this.startAt = startAt;
	}

	public LocalDateTime getEndAt() {
		return endAt;
	}

	public void setEndAt(LocalDateTime endAt) {
		this.endAt = endAt;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public LocalDateTime getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDateTime createAt) {
		this.createAt = createAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
