package it.aman.ethauthserver.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.aman.ethauth.annotations.EthLoggable;
import it.aman.ethauth.model.EthUserPrincipal;

/**
 * @author prg
 *
 */
@Component
public class JwtTokenUtil {

	public static final long TOKEN_VALIDITY = 5 * 60 * 60;

	@Value("${app.jwt.key}")
	private String appSecret;

	@EthLoggable
	public String generateToken(EthUserPrincipal userDetails) {
		if(Objects.isNull(userDetails)) return StringUtils.EMPTY; //On validation empty string should fail
		
		Map<String, Object> claims = new HashMap<>();
		//claims.put also suffices, computeIfAbset() is a must when there is a obj nesting or collection in the map for a key
		claims.computeIfAbsent("sub", val -> userDetails.getUsername());
		claims.computeIfAbsent("id", val -> userDetails.getAccountId());
		return doGenerateToken(claims);
	}

	@EthLoggable
	public boolean validateToken(String token, UserDetails userDetails) {
		Claims claims = getAllClaims(token);
		final String username = claims.getSubject();
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(claims.getExpiration()));
	}
	
	// while creating the token -
	
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the
	// ID
	
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	
	// 3. According to JWS Compact
	// Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
	
	// compaction of the JWT to a URL-safe string
	private String doGenerateToken(Map<String, Object> claims) {
		return Jwts.builder()
				.setClaims(claims)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, appSecret)
				.compact();
	}
	
	private Claims getAllClaims(String token) {
		return Jwts.parser()
				.setSigningKey(appSecret)
				.parseClaimsJws(token)
				.getBody();
	}
	
	private Boolean isTokenExpired(Date expiration) {
		return expiration.before(new Date());
	}
}
