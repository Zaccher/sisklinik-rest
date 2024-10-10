package com.sisklinik.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.sisklinik.dtos.UserappDto;
import com.sisklinik.entities.Userapp;

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

}
