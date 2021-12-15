package com.tcs.ins.user.service.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserModel {

	private Long id;
	@Size(min = 5)
	@NotBlank(message = "Please provide valid user")
	private String name;
	@NotBlank(message = "Please provide valid user")
	private String address;
	private Long mob;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getMob() {
		return mob;
	}

	public void setMob(Long mob) {
		this.mob = mob;
	}

	@Override
	public String toString() {
		return "UserModel [id=" + id + ", name=" + name + ", address=" + address + ", mob=" + mob + "]";
	}
}
