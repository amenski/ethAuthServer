package it.aman.ethauthserver.service.impl;

import javax.naming.AuthenticationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import it.aman.ethauth.annotations.EthLoggable;
import it.aman.ethauth.config.jwt.JwtTokenUtil;
import it.aman.ethauth.model.EthUserPrincipal;
import it.aman.ethauth.service.EthUserDetailServiceImpl;
import it.aman.ethauthserver.service.IAuthenticationService;

@Service
public class AuthenticationService implements IAuthenticationService {

	@Value("${app.jwt.key:}")
	private String secretKey;
	
	@Autowired
	private EthUserDetailServiceImpl userDetailsService;
	
	// configured to use userDetailService in securityConfig.java
	@Autowired
	private DaoAuthenticationProvider authProvider;
	
	@Override
	@EthLoggable
	public String authenticateAndGenerateToken(String userName, String password) throws Exception {
		EthUserPrincipal userDetails = null;

		try {
			if (StringUtils.isAnyEmpty(userName, password))
				throw new AuthenticationException("Insufficient permission.");

			Authentication auth = authProvider.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
			if (auth == null)
				throw new AuthenticationException("Insufficient permission.");

			userDetails = (EthUserPrincipal) userDetailsService.loadUserByUsername(userName);
		} catch (Exception e) {
			throw e;
		}
		return JwtTokenUtil.generateToken(userDetails, secretKey);
	}

}
