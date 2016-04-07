package com.sequencing.oauth.core;

import java.io.Serializable;

/**
 * Class that defines token attributes needed for making data 
 * access requests to sequencing.com backend 
 */
public class Token implements Serializable
{
	private static final long serialVersionUID = -3465943489440860180L;

	/**
	 * Access token value
	 */
	private String accessToken;
	
	/**
	 * Token needed for refreshing access token
	 */
	private String refreshToken;
	
	/**
	 * Access token lifetime
	 */
	private long lifetime = 0;

	public Token(String accessToken, String refreshToken, long lifetime) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.lifetime = lifetime;
	}
	
	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public long getLifeTime() {
		return lifetime;
	}
}
