package it.aman.ethauthserver.util;

/**
 * @author prg
 *
 */
public interface EthAuthserverConstants {
	public static final String SECRET = "secret";
	
	public static final long TOKEN_VALIDITY = 24 * 60 * 60; // 24 hr
	
	public static final String BEARER_TOKEN_PREFIX = "Bearer ";
	
	public static final String AUTHORIZATION_STRING = "Authorization";
}
