package com.example.demo.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
			.antMatchers("/login").permitAll()
			.antMatchers("/signup").permitAll()
			.antMatchers("/addUser").permitAll()
			.antMatchers("/*").hasRole("USER");
		
		http.formLogin()
			.loginPage("/login")
			.usernameParameter("username")
			.passwordParameter("password")
			.loginProcessingUrl("/login");
		
		http.csrf().disable();
		
		http.logout().logoutSuccessUrl("/login");
		
//		http.rememberMe().rememberMeParameter("remember");
	}
	
	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
//		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
//			.withUser("jianbo").password(new BCryptPasswordEncoder().encode("123456")).roles("USER");
		
		auth.jdbcAuthentication().dataSource(dataSource)
			.passwordEncoder(new BCryptPasswordEncoder())
			.usersByUsernameQuery("select name,password,enabled "
					+ "from user "
					+ "where name = ?")
			.authoritiesByUsernameQuery("select name,role "
					+ "from user "
					+ "where name = ?")
			;
	}
	
	

}