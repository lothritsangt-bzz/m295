package com.example.demo295.repository;

import com.example.demo295.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RoomDBRepository extends JpaRepository<Room, Integer> {
}

