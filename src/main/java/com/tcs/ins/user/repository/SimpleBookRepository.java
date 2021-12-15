package com.tcs.ins.user.repository;


import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.tcs.ins.user.repository.entity.Book;

@Component
@CacheConfig(cacheNames = "books")
public class SimpleBookRepository implements BookRepository {
	
	@Cacheable
	@Override
	public Book getByIsbn(String isbn) {
		simulateSlowService();
		return new Book(isbn, "Some book");
	}

	// Don't do this at home
	private void simulateSlowService() {
		try {
			long time = 6000L;
			Thread.sleep(time);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}
}