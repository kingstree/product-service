package com.bookshop.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/actuator/**").permitAll() //엑추에이터 엔드포인트에 인증되지 않은 액세스를 허용한다.
						//.mvcMatchers(HttpMethod.GET, "/", "/books/**").permitAll() 시큐리티 6.x 부터는 지원 x
						.requestMatchers(HttpMethod.GET, "/", "/books/**").permitAll() //인증하지 않고도 인사말과 책의 정보를 제공하도록 허용한다.
						//.anyRequest().authenticated(); //이외 다른 요청은 인증이 필요하다.
						.anyRequest().hasRole("employee")
				)
				.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt) // jwt에 기반한 기본 설정을 사용해 OAth2 리소스 서버 지원을 활성화 한다.
				.sessionManagement(sessionManagement ->//각 요청은 액세스 토큰을 가지고 있어야 하는데, 그래야만 사용자는 요청 간 사용자 세션을 계속 유지할 수 있다.(상태를 갖지 않길 원함)
						sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.csrf(AbstractHttpConfigurer::disable)//인증 전략이 상태를 가지지 않고 브라우저 기반 클라이언트가 관여되지 않기 때문에 CSRF 보호는 비활성화해도 된다.
				.build();
	}

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		var jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
		jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
		jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

		var jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
		return jwtAuthenticationConverter;
	}

}
