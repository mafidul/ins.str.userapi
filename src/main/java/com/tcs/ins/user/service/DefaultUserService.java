package com.tcs.ins.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tcs.ins.user.repository.UserRepository;
import com.tcs.ins.user.repository.entity.User;
import com.tcs.ins.user.service.mapper.UserMapper;
import com.tcs.ins.user.service.model.UserModel;
import com.tcs.ins.user.service.model.UserSearchRequest;
import com.tcs.ins.user.service.specification.UserSpecification;

@Service
public class DefaultUserService implements UserService {

	private final UserMapper userMapper;
	private final UserRepository userRepository;

	public DefaultUserService(UserMapper userMapper, UserRepository userRepository) {
		this.userMapper = userMapper;
		this.userRepository = userRepository;
	}

	@Override
	public UserModel getUserById(Long id) {
		Optional<User> userModel = userRepository.findById(id);
		return userMapper.toModel(userModel.get());
	}

	@Override
	public UserModel createUser(UserModel userModel) {
		User user = userMapper.toEntity(userModel);
		User saveUser = userRepository.save(user);
		return userMapper.toModel(saveUser);
	}

	@Override
	public UserModel updateUser(UserModel userModel) {
		Optional<User> userOptional = userRepository.findById(userModel.getId());
		if (userOptional.isEmpty()) {
			throw new IllegalArgumentException("User Not Found");
		}
		User user = userMapper.toEntity(userModel);
		User saveUser = userRepository.save(user);
		return userMapper.toModel(saveUser);
	}

	@Override
	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}

	@Override
	public Page<UserModel> search(UserSearchRequest userSearchRequest, PageRequest pageRequest) {
		Page<User> page = userRepository.findAll(new UserSpecification(userSearchRequest), pageRequest);
		List<UserModel> pageContent = userMapper.toModel(page.getContent());
		return new PageImpl<UserModel>(pageContent, pageRequest, page.getTotalElements());
	}
}
