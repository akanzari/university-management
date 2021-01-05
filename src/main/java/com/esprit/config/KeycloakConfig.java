package com.esprit.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.preauth.x509.X509AuthenticationFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

public class KeycloakConfig {

	@KeycloakConfiguration
	@ConditionalOnProperty(name = "keycloak.enabled", havingValue = "true", matchIfMissing = true)
	public static class KeycloakConfigurationAdapter extends KeycloakWebSecurityConfigurerAdapter {

		@Bean("keycloak-client")
		public Keycloak keycloakClinet(@Value("${keycloak.credentials.secret}") String secret,
				@Value("${keycloak.realm}") String realm, @Value("${keycloak.auth-server-url}") String authServerUrl,
				@Value("${keycloak.resource}") String ressource) {
			return KeycloakBuilder.builder().serverUrl(authServerUrl).realm(realm).clientId(ressource)
					.clientSecret(secret).resteasyClient(new ResteasyClientBuilder().build())
					.grantType(OAuth2Constants.CLIENT_CREDENTIALS).build();
		}

		/**
		 * Defines the session authentication strategy.
		 */
		@Bean
		@Override
		protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
			// required for bearer-only applications.
			return new NullAuthenticatedSessionStrategy();
		}

		/**
		 * Registers the KeycloakAuthenticationProvider with the authentication manager.
		 */
		@Autowired
		public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
			KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
			// simple Authority Mapper to avoid ROLE_
			keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
			auth.authenticationProvider(keycloakAuthenticationProvider);
		}

		/**
		 * Required to handle spring boot configurations
		 * 
		 * @return
		 */
		@Bean
		public org.keycloak.adapters.KeycloakConfigResolver KeycloakConfigResolver() {
			return new KeycloakSpringBootConfigResolver();
		}

		/**
		 * Configuration spécifique à keycloak (ajouts de filtres, etc)
		 * 
		 * @param http
		 * @throws Exception
		 */
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.sessionManagement()
					// use previously declared bean
					.sessionAuthenticationStrategy(sessionAuthenticationStrategy())

					// keycloak filters for securisation
					.and().addFilterBefore(keycloakPreAuthActionsFilter(), LogoutFilter.class)
					.addFilterBefore(keycloakAuthenticationProcessingFilter(), X509AuthenticationFilter.class)
					.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())

					// add cors options
					.and().cors()
					// delegate logout endpoint to spring security

					.and().logout().addLogoutHandler(keycloakLogoutHandler()).logoutUrl("/logout").logoutSuccessHandler(
							// logout handler for API
							(HttpServletRequest request, HttpServletResponse response,
									Authentication authentication) -> response.setStatus(HttpServletResponse.SC_OK))
					.and().apply(new CommonSpringKeycloakTutorialsSecuritAdapter());

		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
					"/configuration/security", "/swagger-ui.html", "/webjars/**");
		}
	}

	public static class CommonSpringKeycloakTutorialsSecuritAdapter
			extends AbstractHttpConfigurer<CommonSpringKeycloakTutorialsSecuritAdapter, HttpSecurity> {

		@Override
		public void init(HttpSecurity http) throws Exception {
			// any method that adds another configurer
			// must be done in the init method
			http
					// disable csrf because of API mode
					.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

					.and()
					// manage routes securisation here
					.authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()

					// manage routes securisation here
					.and().authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll()

					.antMatchers("/logout", "/", "/unsecured", "/actuator/**").permitAll()

					.anyRequest().permitAll();

		}

	}

}