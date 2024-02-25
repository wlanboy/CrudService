package com.wlanboy.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.wlanboy.demo.security.MapRoles;

@Configuration
@EnableWebSecurity()
@EnableMethodSecurity()
public class BasicWebSecurityConfigurerAdapter {

	@Value("${userpassword}")
	String userpassword;
	
	@Value("${usermodpassword}")
	String usermodpassword;
		
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(new AntPathRequestMatcher("/webjars/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/actuator/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("swagger-ui/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("v3/api-docs/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**")).permitAll()
                        .anyRequest().authenticated())
                .httpBasic(basic -> basic.realmName("wlanboy-crudservice")).sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).csrf(csrf -> csrf.disable());
      return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("user")
            .password(userpassword)
            .roles(MapRoles.SIMPLEOBJECT)
            .build();
        UserDetails usermod = User.withUsername("usermod")
            .password(usermodpassword)
            .roles(MapRoles.SIMPLEOBJECT,MapRoles.MODOBJECT)
            .build();
        return new InMemoryUserDetailsManager(user,usermod);
    }    
 
}
