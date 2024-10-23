package com.sisklinik.entities;

import java.util.Date;

import com.sisklinik.converters.StringTrimConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
	
	@Id
	@Column(name = "id")
	@SequenceGenerator(name="role_generator", sequenceName="role_seq", allocationSize = 1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="role_generator")
	private Integer id;

	@Column(name = "visible", nullable = false)
	private boolean visible;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "name")
	private String name;
	
	@Column(name = "data_inizio_validita")
	private Date dataInizioValidita;
	
	@Column(name = "data_fine_validita")
	private Date dataFineValidita;
	
	

}
