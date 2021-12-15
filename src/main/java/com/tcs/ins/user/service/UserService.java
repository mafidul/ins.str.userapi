package com.tcs.ins.user.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.tcs.ins.user.service.model.UserModel;
import com.tcs.ins.user.service.model.UserSearchRequest;

public interface UserService {

	UserModel getUserById(Long id);

	UserModel createUser(UserModel userModel);

	UserModel updateUser(UserModel userModel);
	
	Page<UserModel> search(UserSearchRequest userSearchRequest, PageRequest pageRequest);

	void deleteUser(Long id);
}
