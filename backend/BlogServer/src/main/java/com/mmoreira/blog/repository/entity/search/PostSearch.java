package com.mmoreira.blog.repository.entity.search;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.mmoreira.blog.repository.entity.Comment;
import com.mmoreira.blog.repository.entity.Post;

public class PostSearch implements Specification<Post>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3069448037566868284L;
	private String postSearch;
	
	public PostSearch(String postSearch) {
		this.postSearch = postSearch;
	}

	@Override
	public Predicate toPredicate(Root<Post> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
		final List<Predicate> predicates = new ArrayList<Predicate>();
		
		if(postSearch != null && !postSearch.isEmpty()) {
			Join<Post, Comment> comments = root.join("comments", JoinType.LEFT);
			
			predicates.add(cb.like(cb.lower(root.<String>get("text")), 
					String.format("%%%s%%", postSearch.toLowerCase().replace("_", "\\_"))));
			predicates.add(cb.like(cb.lower(comments.<String>get("text")), 
					String.format("%%%s%%", postSearch.toLowerCase().replace("_", "\\_"))));
		} else {
			return cb.and();
		}
		
		query.distinct(true);
		return cb.or(predicates.toArray(new Predicate[predicates.size()]));
	}

}
