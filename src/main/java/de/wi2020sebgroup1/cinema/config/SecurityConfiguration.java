package de.wi2020sebgroup1.cinema.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) 
      throws Exception {
        auth.inMemoryAuthentication()
        .withUser("user")
        .password(passwordEncoder().encode("password")).roles("USER")
        .and()
        .withUser("admin")
        .password(passwordEncoder().encode("password")).roles("ADMIN");
    }
    
    protected void configure(HttpSecurity http) throws Exception {
        http
        .cors()
        .configurationSource(corsConfigurationSource())
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers("/login", "/signup").permitAll()
		.antMatchers("/admin/**").hasRole("ADMIN")                             
        .anyRequest().authenticated()
        .and().formLogin()
        .and().httpBasic();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(Arrays.asList("https://wi2020seb-cinema.azurewebsites.net/", "https://wi2020seb-cinema.azurewebsites-dev.net/", "https://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    
}
