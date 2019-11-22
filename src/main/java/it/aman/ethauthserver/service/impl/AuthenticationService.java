package it.aman.ethauthserver.service.impl;

import javax.naming.AuthenticationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import it.aman.ethauth.service.EthUserDetailServiceImpl;
import it.aman.ethauthserver.service.IAuthenticationService;
import it.aman.ethauthserver.util.JwtTokenUtil;

@Service
public class AuthenticationService implements IAuthenticationService {

	@Autowired
	private JwtTokenUtil jwtUtil;
	
	@Autowired
	private EthUserDetailServiceImpl userDetailsService;
	
	// configured to use userDetailService in securityConfig.java
	@Autowired
	private DaoAuthenticationProvider authProvider;
	
	@Override
	public String authenticateAndGenerateToken(String userName, String password) throws Exception {
		if(StringUtils.isAnyEmpty(userName, password))
			throw new AuthenticationException("Insufficient permission.");
		
		Authentication auth = authProvider.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
		if(auth == null)
			throw new AuthenticationException("Insufficient permission.");
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
			
		return jwtUtil.generateToken(userDetails);
	}

}
