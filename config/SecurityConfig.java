//package com.kingsmen.kingsreachdev.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//	    http
//	        .csrf(csrf -> csrf.disable())
//	        .authorizeHttpRequests(auth -> auth
//	            .requestMatchers("/login").permitAll() 
//	            .requestMatchers("/addEmployee").permitAll()
//	            .requestMatchers("/changePassword").permitAll()
//	            .anyRequest().authenticated()
//	        );
//	    return http.build();
//	}
//
//	 @Bean
//	    public PasswordEncoder passwordEncoder() {
//	        return new BCryptPasswordEncoder();
//	    }
//
//}

package com.kingsmen.kingsreachdev.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf(csrf -> csrf.disable())
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/login", "/addEmployee", "/changePassword").permitAll()
				.anyRequest().authenticated()
				)
		.authenticationProvider(authenticationProvider())
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
//		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//		authenticationManagerBuilder
//		.userDetailsService(customUserDetailsService)
//		.passwordEncoder(passwordEncoder());
//		return authenticationManagerBuilder.build();
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(12);
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(customUserDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return provider;
	}
}
