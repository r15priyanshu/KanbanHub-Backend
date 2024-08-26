package com.anshuit.kanbanhub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anshuit.kanbanhub.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
