package com.stlim.shortener.rest;

import com.stlim.shortener.models.UrlInputForm;
import com.stlim.shortener.models.UrlShortener;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShortenerRestControllerTest {
	@Autowired
	private MockMvc mvc;

	@Before
	public void setUp() throws Exception {
		List<String> params = new ArrayList<String>();

		params.add( "{\"url\":\"http://a.com\"}");
		params.add( "{\"url\":\"https://b.net\"}");
		params.add( "{\"url\":\"ftp://c.org\"}");

		for (String s: params) {
			MvcResult result = mvc.perform(
				post("/shortener")
					.content(s)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		}
	}

	@Test
	public void testCreateUrlShortener() throws Exception {
		List<String> params = new ArrayList<String>();
		params.add( "{\"url\":\"http://e.com\"}");
		params.add( "{\"url\":\"https://f.net\"}");
		params.add( "{\"url\":\"ftp://g.org\"}");
		params.add( "{\"url\":\"http://e.com\"}"); // Duplicate
		params.add( "{\"url\":\"https://h.net\",\"short-link\":\"foobar\"}");

		for (String s: params) {
			MvcResult result = mvc.perform(
				post("/shortener")
					.content(s)
					.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		}
	}

	@Test
	public void testCreateUrlShortenerBadRequest() throws Exception {
		List<String> params = new ArrayList<String>();
		params.add("");

		for (String url : params) {
			MvcResult result = mvc.perform(
				post("/shortener")
					.content(url))
				.andExpect(status().isBadRequest())
				.andReturn();
		}
	}

	@Test
	public void testCreateUrlShortenerUnsupportedMediaType() throws Exception {
		List<String> params = new ArrayList<String>();
		params.add("{\"url\":\"\"}");
		params.add("{\"url\":\"abcd\"}");
		params.add("{\"url\":\",\"}");
		params.add("{\"url\":\"http://i.com\",\"short-url\":\"fooba\"}");
		params.add("{\"url\":\"http://j.com\",\"short-url\":\",\"}");

		for (String url : params) {
			MvcResult result = mvc.perform(
				post("/shortener")
					.content(url))
				.andExpect(status().isUnsupportedMediaType())
				.andReturn();
		}
	}


	@Test
	public void testGetUrlById() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("000001", "http://a.com");
		map.put("000002", "https://b.net");
		map.put("000003", "ftp://c.org");
		for (Map.Entry<String, String> e : map.entrySet()) {
			MvcResult result = mvc.perform(
				get("/" + e.getKey()))
				.andExpect(status().is3xxRedirection())
				.andExpect(header().string("Location", e.getValue()))
				.andReturn();
		}
	}

	@Test
	public void testGetUrlByIdInvalidId() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put(",", "Invalid.Id.(,)");
		for (Map.Entry<String, String> e : map.entrySet()) {
			MvcResult result = mvc.perform(
				get("/" + e.getKey()))
				.andExpect(status().isNotAcceptable())
				.andExpect(status().reason(containsString(e.getValue())))
				.andReturn();
		}
	}

	@Test
	public void testGetUrlByIdNotFound() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("zzzzzz", "Url.Not.Found.(zzzzzz)");
		for (Map.Entry<String, String> e : map.entrySet()) {
			MvcResult result = mvc.perform(
				get("/" + e.getKey()))
				.andExpect(status().isNotFound())
				.andExpect(status().reason(containsString(e.getValue())))
				.andReturn();
		}
	}

}
