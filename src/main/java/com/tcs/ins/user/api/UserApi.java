package com.tcs.ins.user.api;

import static com.tcs.ins.support.Constants.REQUEST_MAPPING_USER;
import static com.tcs.ins.support.Constants.REQUEST_PARAM_PAGE_NUMBER;
import static com.tcs.ins.support.Constants.REQUEST_PARAM_PAGE_SIZE;
import static com.tcs.ins.support.Constants.REQUEST_PARAM_SORT_BY;
import static com.tcs.ins.support.Constants.REQUEST_PARAM_SORT_DIRECTION;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Sort.Order;

import com.tcs.ins.user.service.UserService;
import com.tcs.ins.user.service.model.UserModel;
import com.tcs.ins.user.service.model.UserSearchRequest;

@RestController
@RequestMapping(REQUEST_MAPPING_USER)
public class UserApi {

	private final UserService userService;

	public UserApi(UserService userService) {
		this.userService = userService;
	}

	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UserModel> getUserById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}

	@GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<Page<UserModel>> search(
			@RequestParam(name = REQUEST_PARAM_PAGE_NUMBER, required = true) @Positive Integer pageNumber, 
			@RequestParam(name = REQUEST_PARAM_PAGE_SIZE) @Positive Integer pageSize,
			@RequestParam(name = REQUEST_PARAM_SORT_BY, required = false) String sortBy,
			@RequestParam(name = REQUEST_PARAM_SORT_DIRECTION, required = false) String sortDirection,
			@RequestParam Map<String, String> requestParam) {
		UserSearchRequest userSearchRequest = new UserSearchRequest(requestParam);
		PageRequest pageRequest = getByDirection(pageNumber, pageSize, sortBy, sortDirection);

		Page<UserModel> page = userService.search(userSearchRequest, pageRequest);
		return ResponseEntity.ok(page);
	}

	
	private PageRequest getByDirection(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
		if (StringUtils.hasText(sortBy)) {
			Direction direction = StringUtils.hasText(sortDirection) && "asc".equalsIgnoreCase(sortDirection)
					? Direction.ASC
					: Direction.DESC;
			return PageRequest.of(pageNumber, pageSize, Sort.by(new Order(direction, sortBy)));
		} else {
			return PageRequest.of(pageNumber, pageSize);
		}
	}

	@PostMapping(path = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UserModel> createUser(@Valid @RequestBody UserModel userModel, UriComponentsBuilder ucb) {
		UserModel createUser = userService.createUser(userModel);
		return ResponseEntity.created(ucb
									.path(REQUEST_MAPPING_USER + "/{id}")
									.buildAndExpand(createUser.getId())
									.toUri())
							  .body(createUser);
	}

	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<UserModel> updateUser(@Valid @PathVariable Long id, @Valid @RequestBody UserModel userModel) {
		userModel.setId(id);
		UserModel updtaeUser = userService.updateUser(userModel);
		return ResponseEntity.ok(updtaeUser);
	}

	@DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
		return ResponseEntity.ok(null);
	}
}