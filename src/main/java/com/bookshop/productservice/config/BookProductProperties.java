package com.bookshop.productservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "polar") //해당 클래스는 polar로 시작하는 설정 속성에 대한 소스임
public class BookProductProperties {

	/**
	 * A message to welcome users.
	 */
	private String greeting;

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

}
