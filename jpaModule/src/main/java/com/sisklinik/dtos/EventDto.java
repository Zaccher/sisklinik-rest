package com.sisklinik.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventDto {

	private Integer id;
	private LocalDateTime start;
	private LocalDateTime end;
	private String barColor;
	private String text;
	private String note;
	private String resource;
	private String patient;

	// Questi due elementi servono al FE per reindirizzare gli evento come non resize e non draggable
	private boolean resizeDisabled;
	private boolean moveDisabled;

}
