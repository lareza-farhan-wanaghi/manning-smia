// package com.optimagrowth.organization.config;

// import org.keycloak.adapters.KeycloakConfigResolver;
// import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
// import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
// import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
// import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
// import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
// import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
// import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
// import org.springframework.security.core.session.SessionRegistryImpl;
// import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;

// @EnableWebSecurity
// @Configuration
// @EnableGlobalMethodSecurity(jsr250Enabled = true)
// public class SecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         super.configure(http);
//         http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
//     }

//     @Bean
//     @Override
//     public SessionAuthenticationStrategy sessionAuthenticationStrategy() {
//         return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
//     }
    
//     @Autowired
//     public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//         KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
//         keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
//         auth.authenticationProvider(keycloakAuthenticationProvider);
//     }
// }
