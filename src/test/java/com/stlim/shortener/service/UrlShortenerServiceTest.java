package com.stlim.shortener.service;

import com.stlim.shortener.models.UrlShortener;
import org.apache.commons.validator.ValidatorException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlShortenerServiceTest {
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	@Autowired
	UrlShortenerService urlShortenerService;

	@Test
	protected void testUrlShortener() {
		Map<String, Long> test = new HashMap<String, Long>();
		test.put("http://www.google.com", 1L);
		test.put("http://www.facebook.com", 2L);

		for (Map.Entry<String, Long> e : test.entrySet()) {
			try {
				UrlShortener result = urlShortenerService.validateAndSave(e.getKey());
				Assert.assertEquals(result.getId(), e.getValue());
				Assert.assertEquals(result.getUrl(), e.getKey());
			} catch (ValidatorException ex) {
				// Do nothing
			}
		}
	}

	@Test
	protected void testUrlShortenerInvalidUrl() throws ValidatorException {
		exception.expect(ValidatorException.class);
		UrlShortener result = urlShortenerService.validateAndSave("abcd");
	}


}
