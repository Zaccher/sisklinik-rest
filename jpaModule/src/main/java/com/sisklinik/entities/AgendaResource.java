package com.sisklinik.entities;

import org.hibernate.envers.Audited;

import com.sisklinik.converters.StringTrimConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agendaresource")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class AgendaResource {

	@Id
	@Column(name = "id")
	@SequenceGenerator(name="ageresource_generator", sequenceName="ageresource_seq", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ageresource_generator")
	Integer id;
	
	@Column(name = "visible", nullable = false)
	private boolean visible;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "alias", length = 50)
	String alias;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "icon")
	String icon;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "userapp",  referencedColumnName = "id")
	private Userapp userapp;

}
