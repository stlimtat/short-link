package com.stlim.shortener.service;

import com.stlim.shortener.models.UrlShortener;
import org.apache.commons.validator.ValidatorException;

public interface UrlShortenerService {
	UrlShortener validateAndSave(String url) throws ValidatorException;
}

