package com.stlim.shortener.rest;

import com.stlim.shortener.models.UrlShortener;
import com.stlim.shortener.service.UrlShortenerService;
import org.apache.commons.validator.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ShortenerRestController {
	@Autowired
	UrlShortenerService urlShortenerService;

	@PostMapping("/shortener")
	public UrlShortener createUrlShortener(
		@RequestBody String url
	) {
		UrlShortener result;
		try {
			result = urlShortenerService.validateAndSave(url);
		} catch (ValidatorException ex) {
			throw new ResponseStatusException(
				HttpStatus.NOT_ACCEPTABLE, "Invalid.Url", ex);
		}
		return result;
	}


}
