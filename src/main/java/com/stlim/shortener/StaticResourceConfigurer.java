package com.stlim.shortener;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.EncodedResourceResolver;

@Configuration
@EnableWebMvc
public class StaticResourceConfigurer implements WebMvcConfigurer {
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
			.addResourceHandler("/static/**")
			.addResourceLocations("classpath:/static/")
			.setCachePeriod(3600)
			.resourceChain(true)
			.addResolver(new EncodedResourceResolver());
		registry
			.addResourceHandler("/dist/**")
			.addResourceLocations("classpath:/static/dist/")
			.setCachePeriod(3600)
			.resourceChain(true)
			.addResolver(new EncodedResourceResolver());
		registry
			.addResourceHandler("/pages/**")
			.addResourceLocations("classpath:/static/pages/")
			.setCachePeriod(3600)
			.resourceChain(true)
			.addResolver(new EncodedResourceResolver());
		registry
			.addResourceHandler("/plugins/**")
			.addResourceLocations("classpath:/static/plugins/")
			.setCachePeriod(3600)
			.resourceChain(true)
			.addResolver(new EncodedResourceResolver());
	}
}
