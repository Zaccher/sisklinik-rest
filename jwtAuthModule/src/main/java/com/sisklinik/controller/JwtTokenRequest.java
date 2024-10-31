package com.sisklinik.controller;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtTokenRequest implements Serializable {

	private static final long serialVersionUID = -5616176897013108345L;

	private String username;
	private String password;

}
