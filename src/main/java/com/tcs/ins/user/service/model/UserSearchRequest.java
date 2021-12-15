package com.tcs.ins.user.service.model;

import java.util.Map;

import org.springframework.util.StringUtils;

public class UserSearchRequest {
	
	private final Long id;
	private final String name;

	public UserSearchRequest(Map<String, String> requestParam) {
		String idStr = requestParam.get("id");
		if (StringUtils.hasText(idStr)){
			this.id = Long.valueOf(idStr);
		} else {
			this.id=null;
		}
		
		this.name = requestParam.get("name");
	}

	public boolean idFilteringRequired() {
		return id != null;
	}
	
	public boolean nameFilteringRequired() {
		return StringUtils.hasText(name);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
