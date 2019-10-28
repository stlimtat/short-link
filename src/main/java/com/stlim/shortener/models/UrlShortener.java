package com.stlim.shortener.models;

import com.stlim.shortener.service.Base62Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Date;

@Entity
public class UrlShortener {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String url;
	private Date createdAt;

	@Transient
	@Autowired
	Base62Service base62Service;

	protected UrlShortener() {}

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

	// The magic formula
	public String getShortUrl() {
		return base62Service.encode(this.id);
	}

	@PrePersist
	protected void onCreate() {
		createdAt = new Date();
	}
}
