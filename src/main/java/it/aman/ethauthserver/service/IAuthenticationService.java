package it.aman.ethauthserver.service;

public interface IAuthenticationService {

	public String authenticateAndGenerateToken(String userName, String password) throws Exception;
	
}
