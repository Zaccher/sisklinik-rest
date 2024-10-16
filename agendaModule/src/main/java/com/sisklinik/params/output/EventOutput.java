package com.sisklinik.params.output;

import com.sisklinik.dtos.EventDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventOutput extends BasicOutput {
	
	private EventDto eventDto;

}
