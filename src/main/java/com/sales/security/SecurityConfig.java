package com.sales.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{ 
	public final static String[] URLS = new String[]{
			"/", "/showBooks", "/showCustomers", "/showLoans", "/addBook","/addCustomer", "/newLoan", "/deleteLoan"
	};
  @Override
  public void configure(HttpSecurity httpSecurity) throws Exception{
    httpSecurity.authorizeRequests()
     .antMatchers(URLS)
     .hasRole("USER")
     .and()
     .formLogin()
     .and()
     .logout()
     .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
     .logoutSuccessUrl("/");
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) 
						throws Exception {
    auth.inMemoryAuthentication()
      .withUser("lena").password("lena").roles("USER");
  }
}
