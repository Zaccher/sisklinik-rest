package com.sisklinik.entities;

import java.util.Date;

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
@Table(name = "userapp")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class Userapp {

	@Id
	@Column(name = "id")
	@SequenceGenerator(name="userapp_generator", sequenceName="userapp_seq", allocationSize = 1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="userapp_generator")
	private Integer id;
	
	@Column(name = "visible", nullable = false)
	private boolean visible;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "username")
	private String username;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "password")
	private String password;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "name")
	private String name;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "surname")
	private String surname;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "fiscal_code")
	private String fiscalCode;
	
	@Column(name = "birth_date")
	private Date birthDate;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "birth_place")
	private String birthPlace;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "address")
	private String address;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "postcode")
	private String postcode;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "municipality")
	private String municipality;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "district")
	private String district;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "personal_phone")
	private String personalPhone;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "home_phone")
	private String homePhone;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "mail_address")
	private String mailAddress;
	
}
