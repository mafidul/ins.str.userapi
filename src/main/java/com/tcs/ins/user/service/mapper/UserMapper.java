package com.tcs.ins.user.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.tcs.ins.user.repository.entity.User;
import com.tcs.ins.user.service.model.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserModel toModel(User source);
	User toEntity(UserModel source);
	List<UserModel> toModel(List<User> source);
}
