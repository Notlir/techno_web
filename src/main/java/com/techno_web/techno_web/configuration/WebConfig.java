package com.techno_web.techno_web.configuration;

import javax.servlet.Filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
	

	
	
	@Bean
	public FilterRegistrationBean<ShallowEtagHeaderFilter> shallowEtagHeaderFilter() {
	    FilterRegistrationBean<ShallowEtagHeaderFilter> filterRegistrationBean = new FilterRegistrationBean<>( new ShallowEtagHeaderFilter());
	    filterRegistrationBean.addUrlPatterns("/*");
	    filterRegistrationBean.setName("etagFilter");
	    return filterRegistrationBean;
	}
	
	public ShallowEtagHeaderFilter etagFilter() {
	    return new ShallowEtagHeaderFilter();
	}

}
