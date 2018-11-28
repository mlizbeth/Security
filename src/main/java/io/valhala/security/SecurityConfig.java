package io.valhala.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.vaadin.flow.spring.annotation.EnableVaadin;

@Configuration
@EnableWebSecurity
@EnableVaadin
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("p4ssw0rd")).roles("ADMIN")
		.and()
		.withUser("user").password(passwordEncoder().encode("userpass")).roles("USER");
	}
	
	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/login*").permitAll().anyRequest().authenticated().and().formLogin()
		.defaultSuccessUrl("/", true).and().logout().logoutUrl("/logout")
		.deleteCookies("JSESSIONID");
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
