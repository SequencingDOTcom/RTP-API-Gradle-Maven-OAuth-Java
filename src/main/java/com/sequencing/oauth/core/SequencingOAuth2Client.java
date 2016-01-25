package com.sequencing.oauth.core;

import java.util.Map;

import com.sequencing.oauth.config.AuthenticationParameters;
import com.sequencing.oauth.exception.BasicAuthenticationFailedException;

/**
 * Interface that defines basic methods needed for handling OAuth
 * protocol implemented by sequencing.com 
 */
public interface SequencingOAuth2Client
{
	/**
	 * Returns a map of parameters needed for an initial GET redirect to sequencing authentication endpoint
	 * @return Map map with parameters, where key is parameter name
	 */
	public Map<String, String> getHttpParametersForRedirect();

	/**
	 * Returns query needed for initial redirect to sequencing authentication endpoint
	 */
	public String getLoginRedirectUrl();

	/**
	 * Acquires OAuth token
	 * @return Token object containing access token, refresh token and access token lifetime
	 */
	public Token authorize(String responseCode, String responseState) throws IllegalStateException, BasicAuthenticationFailedException ;
	
	/**
	 * Returns token state - whether it's still effective or not
	 */
	public boolean isAuthorized();
	
	/**
	 * Returns configuration parameters needed to carry on authentication
	 * against sequencing.com backend
	 */
	public AuthenticationParameters getAuthenticationParameters();
	
	/**
	 * Returns current token
	 */
	public Token getToken();
}
