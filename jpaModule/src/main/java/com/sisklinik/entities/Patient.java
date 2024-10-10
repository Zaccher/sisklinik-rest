package com.sisklinik.entities;

import java.time.LocalDateTime;

import org.hibernate.envers.Audited;

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
@Table(name = "patient")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class Patient {

	@Id
	@Column(name = "id")
	@SequenceGenerator(name="patient_generator", sequenceName="patient_seq", allocationSize = 1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="patient_generator")
	private Integer id;
	
	@Column(name = "visible", nullable = false)
	private boolean visible;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "name")
	private String name;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "surname")
	private String surname;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "gender")
	private String gender;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "fiscal_code")
	private String fiscalCode;
	
	@Column(name = "birth_time")
	private LocalDateTime birthTime;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "birth_place")
	private String birthPlace;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "residence_address")
	private String residenceAddress;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "residence_postcode")
	private String residencePostcode;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "residence_municipality")
	private String residenceMunicipality;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "residence_district")
	private String residenceDistrict;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "home_address")
	private String homeAddress;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "home_postcode")
	private String homePostcode;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "home_municipality")
	private String homeMunicipality;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "home_district")
	private String homeDistrict;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "personal_phone")
	private String personalPhone;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "home_phone")
	private String homePhone;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "mail_address")
	private String mailAddress;
	
//	@OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
//	private Set<Event> eventi = new HashSet<>();
}
