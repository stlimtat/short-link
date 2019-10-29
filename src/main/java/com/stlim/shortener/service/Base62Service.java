package com.stlim.shortener.service;

import org.apache.commons.validator.ValidatorException;
import org.springframework.stereotype.Service;

@Service
public class Base62Service {
	String base62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	// Encodes a long number to a base62 string
	public String encode(Long value) {
		StringBuilder sb = new StringBuilder();
		while (value != 0) {
			sb.append(base62.charAt((int) (value % 62)));
			value /= 62;
		}
		while (sb.length() < 6) {
			sb.append(0);
		}
		return sb.reverse().toString();
	}

	// Decodes a shortened URL to its original URL.
	public Long decode(String s) throws ValidatorException {
		Long result = 0L;

		String base62Encoded = s.substring(s.lastIndexOf("/") + 1);
		for (int i = 0; i < base62Encoded.length(); i++) {
			result = result * 62 + base62.indexOf("" + base62Encoded.charAt(i));
		}
		if (result < 0L) {
			throw new ValidatorException("decode.invalid.(" + s + ")");
		}

		return result;
	}


}
