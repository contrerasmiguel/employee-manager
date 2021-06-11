package io.leantech.employeemanager.entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
	List<Position> findByName(String name);
}
