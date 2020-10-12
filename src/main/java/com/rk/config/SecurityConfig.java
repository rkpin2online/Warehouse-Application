package com.rk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) 
			throws Exception
	{
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http)
			throws Exception 
	{
		http.authorizeRequests()
		.antMatchers("/user/login").permitAll()
		.antMatchers("/user**").hasAuthority("ADMIN")
		.anyRequest().hasAuthority("EMPLOYEE")
		
		.and()
		.formLogin()
		.loginProcessingUrl("/login").permitAll()
		.loginPage("/user/login")
		.defaultSuccessUrl("/user/setup",true)
		.failureUrl("/user/login?error")
		
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
		.logoutSuccessUrl("/user/login?logout")
		
		.and()
		.exceptionHandling()
		.accessDeniedPage("/user/denied");
		
		
	}
}
