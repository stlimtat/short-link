package com.stlim.shortener.rest;

import com.stlim.shortener.models.UrlShortener;
import com.stlim.shortener.service.UrlShortenerService;
import org.apache.commons.validator.ValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
				HttpStatus.NOT_ACCEPTABLE, "Invalid.Url.(" + url + ")", ex);
		}
		return result;
	}

	@GetMapping("/{id}")
	public void getUrlById(
		HttpServletResponse response,
		@PathVariable("id") String id
	) throws IOException {
		UrlShortener result = null;
		try {
			result = urlShortenerService.getUrlById(id);
		} catch (ValidatorException ex) {
			throw new ResponseStatusException(
				HttpStatus.NOT_ACCEPTABLE, "Invalid.Id.(" + id + ")", ex);
		}

		if (result != null) {
			response.sendRedirect(result.getUrl());
		} else {
			throw new ResponseStatusException(
				HttpStatus.NOT_FOUND, "Url.Not.Found.(" + id + ")");
		}
	}


}
