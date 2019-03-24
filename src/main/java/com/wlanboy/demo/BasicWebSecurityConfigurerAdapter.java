package com.wlanboy.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.wlanboy.demo.security.MapRoles;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class BasicWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

	@Value("${userpassword}")
	String userpassword;
	
	@Value("${usermodpassword}")
	String usermodpassword;
	
	//@Autowired
    //private MyBasicAuthenticationEntryPoint authenticationEntryPoint;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().
        realmName("wlanboy-crudservice").
        and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
        and().csrf().disable().
        authorizeRequests().antMatchers("/hello/**").permitAll().anyRequest().authenticated();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .passwordEncoder(passwordEncoder())
            .withUser("user").password(userpassword).roles(MapRoles.SIMPLEOBJECT)
            .and()
            .withUser("usermod").password(usermodpassword).roles(MapRoles.SIMPLEOBJECT,MapRoles.MODOBJECT);
    }    
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }    
}
