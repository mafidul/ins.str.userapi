package com.tcs.ins.user.repository;

import com.tcs.ins.user.repository.entity.Book;

public interface BookRepository {
	Book getByIsbn(String isbn);
}
