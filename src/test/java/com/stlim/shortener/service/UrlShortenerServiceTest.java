package com.stlim.shortener.service;

import com.stlim.shortener.models.UrlInputForm;
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
	public void testUrlShortener() {
		Map<UrlInputForm, Long> test = new HashMap<UrlInputForm, Long>();
		test.put(new UrlInputForm("http://www.google.com", ""), 1L);
		test.put(new UrlInputForm("http://www.facebook.com", ""), 2L);
		test.put(new UrlInputForm("http://www.internet.com", "foobar"), 14102387347L);

		for (Map.Entry<UrlInputForm, Long> e : test.entrySet()) {
			try {
				UrlShortener result = urlShortenerService.validateAndSave(e.getKey());
				Assert.assertEquals(result.getId(), e.getValue());
				Assert.assertEquals(result.getUrl(), e.getKey().getUrl());
				Assert.assertTrue(result.getShortUrl().contains(e.getKey().getShortLink()));
			} catch (ValidatorException ex) {
				// Do nothing
			}
		}
	}

	@Test
	public void testUrlShortenerInvalidUrl() throws ValidatorException {
		exception.expect(ValidatorException.class);
		UrlShortener result = urlShortenerService.validateAndSave(new UrlInputForm("abcd", ""));
	}

	@Test
	public void testUrlShortenerDuplication() {
		Map<UrlInputForm, Long> test = new HashMap<UrlInputForm, Long>();
		test.put(new UrlInputForm("http://www.google.com", ""), 1L);
		test.put(new UrlInputForm("http://www.google.com", ""), 1L);
		test.put(new UrlInputForm("http://www.google.com", "foobar"), 1L);

		for (Map.Entry<UrlInputForm, Long> e : test.entrySet()) {
			try {
				UrlShortener result = urlShortenerService.validateAndSave(e.getKey());
				Assert.assertEquals(result.getId(), e.getValue());
				Assert.assertEquals(result.getUrl(), e.getKey().getUrl());
			} catch (ValidatorException ex) {
				// Do nothing
			}
		}
	}
}
