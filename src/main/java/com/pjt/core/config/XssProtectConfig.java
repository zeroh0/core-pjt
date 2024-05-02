package com.pjt.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjt.core.common.util.XssProtectSupport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class XssProtectConfig {

	 private final ObjectMapper mapper;

	    @Bean
	    public MappingJackson2HttpMessageConverter characterEscapeConverter() {
	        ObjectMapper objectMapper = mapper.copy();
	        objectMapper.getFactory().setCharacterEscapes(new XssProtectSupport());
	        return new MappingJackson2HttpMessageConverter(objectMapper);
	    }


}
