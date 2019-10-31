package com.stlim.shortener.models;

import javax.validation.constraints.NotEmpty;

public class UrlInputForm {
	@NotEmpty(message="inputForm.notEmpty")
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
