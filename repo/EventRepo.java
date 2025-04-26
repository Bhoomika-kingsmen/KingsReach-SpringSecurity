package com.kingsmen.kingsreachdev.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kingsmen.kingsreachdev.entity.Event;

public interface EventRepo extends JpaRepository<Event, Integer> {

}
