package com.sisklinik.dtos;

import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgendaResourceDto {
	
	private String name;
	private String id;
	private String icon;
	
	@Lob
    private byte[] displayPicture;
	

}
