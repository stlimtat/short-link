package com.stlim.shortener.models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UrlShortener {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String url;
	private Date createdAt;

	@Transient
	private String shortUrl;

	protected UrlShortener() {
	}

	public UrlShortener(String url) {
		this.url = url;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	@PrePersist
	protected void onCreate() {
		createdAt = new Date();
	}
}
