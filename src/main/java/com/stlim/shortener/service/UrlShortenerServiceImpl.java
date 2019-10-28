package com.stlim.shortener.service;

import com.stlim.shortener.jpa.UrlShortenerCrudRepository;
import com.stlim.shortener.models.UrlShortener;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UrlShortenerServiceImpl implements UrlShortenerService {
	@Autowired
	UrlShortenerCrudRepository crudRepository;

	public UrlShortener validateAndSave(String url) throws ValidatorException {
		UrlShortener result;
		// check if url is valid
		UrlValidator urlValidator = new UrlValidator();
		if (!urlValidator.isValid(url)) {
			throw new ValidatorException("urlValidator.isValid.(" + url + ")");
		}

		// check if url already exists in db
		Long resultId;
		List<UrlShortener> l = crudRepository.findByUrl(url);
		if (l.size() > 0) {
			// There is already an existing url
			result = l.get(0);
			resultId = result.getId();
		} else {
			// save url if not found
			result = crudRepository.save(new UrlShortener(url));
			resultId = result.getId();
		}

		return result;
	}
}
