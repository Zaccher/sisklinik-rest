package com.sisklinik.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "userapp_role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserappRole {
	
	@Id
	@Column(name = "id")
	@SequenceGenerator(name="userapp_role_generator", sequenceName="userapp_role_seq", allocationSize = 1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="userapp_role_generator")
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "userapp_id",  referencedColumnName = "id")
	@JsonBackReference //dipendenza ciclica -> in questo modo non avrà un riferimento allo userapp
	private Userapp userapp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id",  referencedColumnName = "id")
	private Role role;

}
