package com.sisklinik.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sisklinik.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

	@Query("from Role r where r.visible = true")
	public List<Role> findAllVisible();
}
