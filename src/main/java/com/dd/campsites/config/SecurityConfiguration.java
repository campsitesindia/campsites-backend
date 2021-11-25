package com.dd.campsites.config;

import com.dd.campsites.campsitesindia.security.oauth2.CustomOAuth2UserService;
import com.dd.campsites.campsitesindia.security.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.dd.campsites.campsitesindia.security.oauth2.OAuth2AuthenticationFailureHandler;
import com.dd.campsites.campsitesindia.security.oauth2.OAuth2AuthenticationSuccessHandler;
import com.dd.campsites.security.*;
import com.dd.campsites.security.jwt.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;
import tech.jhipster.config.JHipsterProperties;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JHipsterProperties jHipsterProperties;

    private final TokenProvider tokenProvider;

    private final CorsFilter corsFilter;
    private final SecurityProblemSupport problemSupport;

    private final CustomOAuth2UserService customOAuth2UserService;

    private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;

    private final OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler;

    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    /*
         By default, Spring OAuth2 uses HttpSessionOAuth2AuthorizationRequestRepository to save
         the authorization request. But, since our service is stateless, we can't save it in
         the session. We'll save the request in a Base64 encoded cookie instead.
       */
    @Bean
    public HttpCookieOAuth2AuthorizationRequestRepository cookieAuthorizationRequestRepository() {
        return new HttpCookieOAuth2AuthorizationRequestRepository();
    }

    public SecurityConfiguration(
        TokenProvider tokenProvider,
        CorsFilter corsFilter,
        JHipsterProperties jHipsterProperties,
        SecurityProblemSupport problemSupport,
        CustomOAuth2UserService customOAuth2UserService,
        OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler,
        OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler,
        HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository
    ) {
        this.tokenProvider = tokenProvider;
        this.corsFilter = corsFilter;
        this.problemSupport = problemSupport;
        this.jHipsterProperties = jHipsterProperties;
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2AuthenticationSuccessHandler = oAuth2AuthenticationSuccessHandler;
        this.oAuth2AuthenticationFailureHandler = oAuth2AuthenticationFailureHandler;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web
            .ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/h2-console/**")
            .antMatchers("/swagger-ui/**")
            .antMatchers("/test/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http

            .csrf()
            .disable()
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
                .authenticationEntryPoint(problemSupport)
                .accessDeniedHandler(problemSupport)
        .and()
            .headers()
            .contentSecurityPolicy(jHipsterProperties.getSecurity().getContentSecurityPolicy())
        .and()
            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
        .and()
            .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; fullscreen 'self'; payment 'none'")
        .and()
            .frameOptions()
            .deny()
        .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
            .authorizeRequests()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/upload/**").permitAll()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/account/reset-password/init").permitAll()
            .antMatchers("/api/account/reset-password/finish").permitAll()
            .antMatchers("/api/locations/**").permitAll()
            .antMatchers("/api/listings/**").permitAll()
            .antMatchers("/api/listings**/**").permitAll()
            .antMatchers("/api/features/**").permitAll()
            .antMatchers("/api/photos/**").permitAll()
            .antMatchers("/api//payment/getPaymentForm").permitAll()
            .antMatchers("/api/room/**").permitAll()
            .antMatchers("/api/admin/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/health/**").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN)

        .and()
            .httpBasic()
        .and()
            .apply(securityConfigurerAdapter())
        .and()
            .oauth2Login()
            .authorizationEndpoint()
            .baseUri("/oauth2/authorize")
            .authorizationRequestRepository(cookieAuthorizationRequestRepository())
            .and()
            .redirectionEndpoint()
            .baseUri("/oauth2/callback/*")
            .and()
            .userInfoEndpoint()
                .userService(customOAuth2UserService)
            .and()
            .successHandler(oAuth2AuthenticationSuccessHandler)
            .failureHandler(oAuth2AuthenticationFailureHandler);
        // @formatter:on
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }
}
