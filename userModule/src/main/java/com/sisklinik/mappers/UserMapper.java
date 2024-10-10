package com.sisklinik.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.sisklinik.dtos.UserappDto;
import com.sisklinik.entities.Userapp;
import com.sisklinik.params.input.UserappParamsInput;

@Mapper
public interface UserMapper {
	
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	
	@Mapping(source = "id", target = "id")
	@Mapping(source = "username", target = "username")
	@Mapping(source = "password", target = "password")
	@Mapping(source = "name", target = "name")
	@Mapping(source = "surname", target = "surname")
	@Mapping(source = "fiscalCode", target = "fiscalCode")
	@Mapping(source = "mailAddress", target = "mailAddress")
	UserappDto userappToUserappDto(Userapp userapp);
	
	List<UserappDto> listUserappToListUserappDto(List<Userapp> lista);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "visible", constant = "true")
	@Mapping(source = "username", target = "username")
	@Mapping(source = "password", target = "password")
	@Mapping(source = "name", target = "name")
	@Mapping(source = "surname", target = "surname")
	@Mapping(source = "fiscalCode", target = "fiscalCode")
	@Mapping(source = "birthDate", target = "birthDate")
	@Mapping(source = "birthPlace", target = "birthPlace")
	@Mapping(source = "address", target = "address")
	@Mapping(source = "postcode", target = "postcode")
	@Mapping(source = "municipality", target = "municipality")
	@Mapping(source = "district", target = "district")
	@Mapping(source = "personalPhone", target = "personalPhone")
	@Mapping(source = "homePhone", target = "homePhone")
	@Mapping(source = "mailAddress", target = "mailAddress")
	Userapp userappParamsInputToUserapp(UserappParamsInput input);

}
