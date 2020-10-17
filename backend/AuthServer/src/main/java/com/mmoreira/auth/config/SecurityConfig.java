package com.mmoreira.auth.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mmoreira.auth.repository.AuthorityRepository;
import com.mmoreira.auth.repository.UserRepository;
import com.mmoreira.auth.repository.entity.Authority;
import com.mmoreira.auth.repository.entity.User;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthorityRepository authorityRepository;

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean(name = "authenticationManager")
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder);
	}
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/**").permitAll()
				.antMatchers("/oauth/token**").permitAll()
				.antMatchers("/oauth/authorize**").permitAll()
				.antMatchers("/oauth/check_token**").permitAll()
			.and()
				.formLogin().loginPage("/login").permitAll()
			.and()
				.logout().permitAll()
			.and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			.and()
				.csrf().disable();
    }
	
	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsService() {
			@Override
			public UserDetails loadUserByUsername(String userName) {
				User user = userRepository.findByEmail(userName);
				if (user == null) {
					throw new UsernameNotFoundException("Usuário não encontrado.");
				}
				List<Authority> authorities = authorityRepository.findByUserCode(user.getCode());
				List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
				for (Authority authority : authorities) {
					GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authority.getAuthority().name());
					grantedAuthorities.add(grantedAuthority);
				}
				UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantedAuthorities);
				return userDetails;
			}
		};
	}
}
