package com.tcs.ins.user.service.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.tcs.ins.user.repository.entity.User;
import com.tcs.ins.user.service.model.UserSearchRequest;

public class UserSpecification implements Specification<User> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private final UserSearchRequest userSearchRequest;

	public UserSpecification(UserSearchRequest userSearchRequest) {
		this.userSearchRequest = userSearchRequest;
	}

	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		if (userSearchRequest.idFilteringRequired()) {
			predicates.add(criteriaBuilder.equal(root.get("id"), userSearchRequest.getId()));
		}
		if (userSearchRequest.nameFilteringRequired()) {
			predicates.add(criteriaBuilder.like(root.get("name"), userSearchRequest.getName() + "%"));
		}
		return andTogether(predicates, criteriaBuilder);
	}

	private Predicate andTogether(List<Predicate> predicates, CriteriaBuilder cb) {
		return cb.and(predicates.toArray(new Predicate[0]));
	}
}
