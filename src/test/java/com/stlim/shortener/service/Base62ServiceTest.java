package com.stlim.shortener.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Base62ServiceTest {
	@Rule
	public final ExpectedException thrown = ExpectedException.none();
	Base62Service base62Service;

	@Before
	public void setUp() {
		this.base62Service = new Base62Service();
	}

	@Test
	public void testEncode() {
		String result;
		result = base62Service.encode(0L);
		Assert.assertNotNull(result);
		result = base62Service.encode(Long.MAX_VALUE);
		Assert.assertNotNull(result);
		result = base62Service.encode(99L);
		Assert.assertNotNull(result);
	}

	@Test
	public void testEncodeNegative() {
		thrown.expect(StringIndexOutOfBoundsException.class);
		String result;
		result = base62Service.encode(-65L);
		Assert.assertNotNull(result);
	}


	@Test
	public void testDecode() {
		Long result;
		result = base62Service.decode("abcde");
		Assert.assertNotNull(result);
		result = base62Service.decode("0");
		Assert.assertNotNull(result);
		result = base62Service.decode("ZZZZZZ");
		Assert.assertNotNull(result);
		result = base62Service.decode("abc");
		Assert.assertNotNull(result);
	}

}
