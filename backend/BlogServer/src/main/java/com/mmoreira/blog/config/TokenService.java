package com.mmoreira.blog.config;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

public class TokenService implements ResourceServerTokenServices {
		
		@Value("${check.token.url}")
		private String checkTokenUrl;
		
		@Value("${oauth2.client.id}")
		private String client_id;
		
		@Value("${oauth2.client.secret}")
		private String client_secret;

	    private RestOperations restTemplate;

	    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

	    @Autowired
	    public TokenService() {
	        restTemplate = new RestTemplate();
	        ((RestTemplate) restTemplate).setErrorHandler(new DefaultResponseErrorHandler() {
	            @Override
	            public void handleError(ClientHttpResponse response) throws IOException {
	                if (response.getRawStatusCode() != 400) {
	                    super.handleError(response);
	                }
	            }
	        });
	    }

	    @Override
	    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
	        HttpHeaders headers = new HttpHeaders();
	        String authHeader = String.format("Basic %s", Base64.getEncoder().encodeToString(String.format("%s:%s", client_id, client_secret).getBytes()));
	        headers.add("Authorization", authHeader);
	        
	        Map<String, Object> map = executeGet(String.format("%s?token=%s",checkTokenUrl, accessToken), headers);
	        
	        if (map == null || map.isEmpty() || map.get("error") != null) {
	            throw new InvalidTokenException("Token invalido");
	        }
	        return tokenConverter.extractAuthentication(map);
	    }

	    @Override
	    public OAuth2AccessToken readAccessToken(String accessToken) {
	        throw new UnsupportedOperationException("não suportado: read access token");
	    }

	    private Map<String, Object> executeGet(String path, HttpHeaders headers) {
	        try {
	            if (headers.getContentType() == null) {
	                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	            }
	            @SuppressWarnings("rawtypes")
	            Map map = restTemplate.exchange(path, HttpMethod.GET, new HttpEntity<MultiValueMap<String, String>>(null, headers), Map.class).getBody();
	            @SuppressWarnings("unchecked")
	            Map<String, Object> result = map;
	            return result;
	        } catch (Exception ex) {
	            System.out.println(ex.getMessage());
	        }
	        return null;
	    }

}
