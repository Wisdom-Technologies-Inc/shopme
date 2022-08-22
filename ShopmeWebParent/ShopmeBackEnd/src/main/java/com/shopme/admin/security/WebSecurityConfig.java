package com.shopme.admin.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService(){
		return new ShopemeUserDetailsService();
	}

	public DaoAuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());

		return authenticationProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/users/**").hasAnyAuthority("Admin")
				.antMatchers("/categories/**", "/brands/**").hasAnyAuthority("Admin", "Editor")
				.antMatchers("/products/**").hasAnyAuthority("Admin", "Salesperson", "Editor", "Shipper")
				.antMatchers("/customers/**", "/shippings/**").hasAnyAuthority("Admin", "Salesperson")
				.antMatchers("/orders/**").hasAnyAuthority("Admin", "Salesperson", "Shipper")
				.antMatchers("/reports/**").hasAnyAuthority("Admin", "Salesperson")
				.antMatchers("/articles/**", "/menus/**").hasAnyAuthority("Admin", "Editor")
//				.antMatchers("/brands/**").hasAnyAuthority("Admin", "Editor")
				.anyRequest().authenticated()
				.and()
				.formLogin()
					.loginPage("/login")
					.usernameParameter("email")
					.permitAll()
				.and()
				.logout().permitAll()
				.and()
					.rememberMe()
						.key("AbcDefGhiJklMnoPqrStuVwxYz_1234567890")
						.tokenValiditySeconds(7 * 24 * 60 * 60);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
	}
}
