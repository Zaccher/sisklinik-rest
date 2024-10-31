package com.sisklinik.controller;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class JwtTokenResponse implements Serializable 
{

	private static final long serialVersionUID = 8317676219297719109L;

	private final String token;

}