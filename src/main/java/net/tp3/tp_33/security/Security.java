package net.tp3.tp_33.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class Security {
    @Autowired
    public PasswordEncoder passwordEncoder;


    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                User.withUsername("ayyoub").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build(),
                User.withUsername("user").password(passwordEncoder.encode("1234")).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("1234")).roles("USER","ADMIN").build()


        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/login", "/webjars/**").permitAll()
                        .requestMatchers("user/**").hasRole("USER")
                        .requestMatchers("admin/**").hasRole("ADMIN")


                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex->ex.accessDeniedPage("/NotAuthorized"))

                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/user/index", true)

                )
                .build();
    }


}
