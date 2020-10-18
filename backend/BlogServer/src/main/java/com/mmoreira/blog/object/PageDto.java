package com.mmoreira.blog.object;

import java.util.List;

import org.springframework.data.domain.Page;

public class PageDto<T> {
	private Integer pageNumber;
	private Integer totalPages;
	private List<T> content;
	
	public PageDto(Page<T> content) {
		this.pageNumber = content.getNumber();
		this.totalPages = content.getTotalPages();
		this.content = content.getContent();
	}
	
	public PageDto(Integer pageNumber, Integer totalPages, List<T> content) {
		super();
		this.pageNumber = pageNumber;
		this.totalPages = totalPages;
		this.content = content;
	}
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}
	public List<T> getContent() {
		return content;
	}
	public void setContent(List<T> content) {
		this.content = content;
	}
}
