package com.sisklinik.entities;

import java.time.LocalDateTime;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "event")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Audited
public class Event {

	@Id
	@Column(name = "id")
	@SequenceGenerator(name="event_generator", sequenceName="event_seq", allocationSize = 1) 
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="event_generator")
	private Integer id;

	@Column(name = "visible", nullable = false)
	private boolean visible;
	
	@Column(name = "event_start")
	private LocalDateTime start;
	
	@Column(name = "event_end")
	private LocalDateTime end;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "barcolor")
	private String barColor;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "text")
	private String text;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(name = "note")
	private String note;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "resource",  referencedColumnName = "id")
	private AgendaResource resource;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "patient",  referencedColumnName = "id")
	private Patient patient;
	
}
