package com.mmoreira.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuthSecurityConfig extends AuthorizationServerConfigurerAdapter{
	 private String clientid = "framework_new_blog";
	 private String clientSecret = "framework_secret";
	 
	 @Value("${client.redirect.uri}")
	 private String clienteRedirectUri;
	 
	 @Value("${client.redirect.uri2}")
	 private String clienteRedirectUri2;
	 
	 //TODO usar chaves RSA ao inves de HMAC
	 private String privateKey = "f6891b4ebb65de4ad11b134e6cfd648b726e2c27";
	 private String publicKey = "f6891b4ebb65de4ad11b134e6cfd648b726e2c27";
	 
	 @Autowired
	 @Qualifier("authenticationManager")
	 private AuthenticationManager authenticationManager;
	 
	 @Autowired
	 PasswordEncoder passwordEncoder;
	 
	 @Bean
	 public JwtAccessTokenConverter tokenEnhancer() {
	      JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	      converter.setSigningKey(privateKey);
	      converter.setVerifierKey(publicKey);
	      return converter;
	 }
	 
	 //MExi aQUI QUALQUE COISA
	 @Override 
	 public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception { 
		 oauthServer.checkTokenAccess("isAuthenticated()");
	 }
	 
	 @Bean
	 public JwtTokenStore tokenStore() {
		 return new JwtTokenStore(tokenEnhancer());
	 }
	 
	 @Override
	 public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		 endpoints.authenticationManager(authenticationManager).tokenStore(tokenStore())
		 .accessTokenConverter(tokenEnhancer());
	 }
	 
	 
	 //TODO usar token store no banco de dados ao inves de hardcoded em memoria
	 @Override
	 public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		 clients.inMemory().withClient(clientid).secret(passwordEncoder.encode(clientSecret)).scopes("read", "write")
		 	.authorizedGrantTypes("password", "refresh_token", "authorization_code", "implicit")
		 	.redirectUris(clienteRedirectUri, clienteRedirectUri2)
		 	.autoApprove(true)
		 	.accessTokenValiditySeconds(3600)
		 	.refreshTokenValiditySeconds(3600);
	 }
}
