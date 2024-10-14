package com.sisklinik.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sisklinik.entities.Userapp;


public interface UserappRepository extends JpaRepository<Userapp, Integer> {

	@Query("from Userapp u where u.username = :username and u.password = :password and u.visible = true")
	public Userapp findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
	
	@Query("from Userapp u where u.fiscalCode = :fiscalCode and u.visible = true")
	public Userapp findByFiscalCode(@Param("fiscalCode") String fiscalCode);
}
