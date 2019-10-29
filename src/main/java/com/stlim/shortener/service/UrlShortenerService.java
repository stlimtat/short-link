package com.stlim.shortener.service;

import com.stlim.shortener.jpa.UrlShortenerCrudRepository;
import com.stlim.shortener.models.AppProperty;
import com.stlim.shortener.models.UrlShortener;
import org.apache.commons.validator.ValidatorException;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UrlShortenerService {
	@Autowired
	UrlShortenerCrudRepository crudRepository;
	@Autowired
	Base62Service base62Service;
	@Autowired
	AppProperty appProperty;

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
		} else {
			// save url if not found
			result = crudRepository.save(new UrlShortener(url));
		}
		result.setShortUrl(
			appProperty.getUrl() +
				base62Service.encode(result.getId()));

		return result;
	}

	public UrlShortener getUrlById(String id) throws ValidatorException {
		UrlShortener result = null;
		Long resultId = base62Service.decode(id);
		Optional<UrlShortener> queryResult = crudRepository.findById(resultId);

		if (queryResult.isPresent()) {
			result = queryResult.get();
		}

		return result;
	}
}
