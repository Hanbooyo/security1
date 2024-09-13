package com.cos.security1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    //해당 메서드의 리턴되는 오브젝트를 IoC로 등록해줌 (Bean 어노테이션)
    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                .csrf((csrf) -> csrf.disable())

                .authorizeHttpRequests((authorizeHttpRequests) ->

                        authorizeHttpRequests

                                .requestMatchers("/user/**").authenticated().requestMatchers("/admin/**").hasRole("ADMIN").requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")

                                .anyRequest().permitAll()

                )

                .formLogin((formLogin) ->

                        formLogin

                                // .use rnameParameter("username") // 파라미터로 보낼 이름값 설정하는 부분. username이라고 안쓰고 다른이름 쓰고 싶은 경우 for loadUserByUsername

                                // .passwordParameter("password")

                                .loginPage("/login")

                                // .failureUrl("/authentication/login?failed")

                                .loginProcessingUrl("/loginProc") // login주소가 호출되면 시큐리티가 낚아채서 대신 로그인 진행

                                .defaultSuccessUrl("/")

                )

                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/loginForm") // OAuth2 로그인 페이지 설정
                );

        return http.build();

    }


}
