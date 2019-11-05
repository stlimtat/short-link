package com.stlim.shortener.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

public class UrlInputForm {
	@NotEmpty(message="inputForm.url.notEmpty")
	private String url;

	@Pattern(regexp = "^(|[0-9A-Za-z]{6})$", message = "inputForm.shortLink.incorrectPattern")
	private String shortLink;

	public UrlInputForm() { }

	public UrlInputForm(String url, String shortLink) {
		this.url = url;
		this.shortLink = shortLink;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getShortLink() {
		return shortLink;
	}

	public void setShortLink(String shortLink) {
		this.shortLink = shortLink;
	}
}
