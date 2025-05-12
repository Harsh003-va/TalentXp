package com.User.User.security;


import com.User.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class MySecurity {
@Autowired
private UserService userService;


@Autowired
private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder=
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
        AuthenticationManager authenticationManager= authenticationManagerBuilder.build();

//        http.csrf(csrf->csrf.disable());
//
//              http  .authorizeHttpRequests(authz -> authz
//                        .requestMatchers(new AntPathRequestMatcher("/users/**")).permitAll()
//
//
////                        .requestMatchers(new AntPathRequestMatcher("/users/h2-console/**")).permitAll()
//
//                ).addFilter(new MyAuthenticationFilter(authenticationManager,userService,environment))
//                      .addFilter(new AuthorizationFilter(authenticationManager,environment))
//                      .authenticationManager(authenticationManager)
//                      .sessionManagement(session->session
//                      .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

               //http.formLogin(Customizer.withDefaults());
//               http.httpBasic(Customizer.withDefaults());
        http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(authz->authz
                        .requestMatchers("/users/**")
                        .permitAll()
                        .requestMatchers("/home/**")
                        .permitAll()
                        .requestMatchers("/users/delete/**").hasAuthority("ROLE_ADMIN")
                        .anyRequest().authenticated())
                .addFilter(new MyAuthenticationFilter(authenticationManager))
                .addFilter(new AuthorizationFilter(authenticationManager))
                .authenticationManager(authenticationManager)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

http.headers(headers->headers.frameOptions(frameOptions->frameOptions.sameOrigin()));

        return http.build();
    }
}
