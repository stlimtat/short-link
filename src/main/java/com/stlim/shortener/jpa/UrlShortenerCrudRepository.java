package com.stlim.shortener.jpa;

import com.stlim.shortener.models.UrlShortener;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UrlShortenerCrudRepository extends CrudRepository<UrlShortener, Long> {
	// Magically do nothing.  JPA will do the magic for you
	List<UrlShortener> findByUrl(String url);
}
