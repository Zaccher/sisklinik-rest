package com.sisklinik.params.input;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventParamsInput {

	private Integer id;
	
	@NotNull(message = "{NotNull.Event.start.Validation}")
	private LocalDateTime start;
	
	@NotNull(message = "{NotNull.Event.end.Validation}")
	private LocalDateTime end;
	
	@NotNull(message = "{NotNull.Event.barColor.Validation}")
	@NotBlank(message = "{NotBlank.Event.barColor.Validation}")
	private String barColor;
	
	@NotNull(message = "{NotNull.Event.text.Validation}")
	@NotBlank(message = "{NotBlank.Event.text.Validation}")
	private String text;
	
	private String note;
	
	@NotNull(message = "{NotNull.Event.resource.Validation}")
	@NotBlank(message = "{NotBlank.Event.resource.Validation}")
	private String resource;
	
	@NotNull(message = "{NotNull.Event.patient.Validation}")
	@NotBlank(message = "{NotBlank.Event.patient.Validation}")
	private String patient;
}
