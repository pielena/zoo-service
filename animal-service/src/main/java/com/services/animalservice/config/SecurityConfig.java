package com.services.animalservice.config;

import com.services.animalservice.filter.AuthorizationFilter;
import com.services.animalservice.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenService tokenService;

    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

//    private final AuthenticationConfiguration authenticationConfiguration;
//
//    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration) {
//        this.authenticationConfiguration = authenticationConfiguration;
//    }

//    @Bean
//    public AuthenticationManager authenticationManager() throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()
//                .httpBasic()
//                .and()
//                .logout().permitAll()
//                .and()
                .addFilterBefore(new AuthorizationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http
//                .csrf().disable()
//                .httpBasic().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers("/registration").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .httpBasic()
//                .and()
//                .logout().permitAll();
//
//        return http.build();
//    }
}
