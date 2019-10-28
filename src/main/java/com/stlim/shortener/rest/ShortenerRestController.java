package com.stlim.shortener.rest;

import com.stlim.shortener.models.UrlShortener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShortenerRestController {

	@PostMapping("/shortener")
	public UrlShortener createUrlShortener(
		@RequestParam(value = "url") String url
	) {
		return null;
	}


}
