package it.aman.ethauthserver.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiParam;
import it.aman.ethauth.annotations.EthLoggable;
import it.aman.ethauthserver.service.impl.AuthenticationService;
import it.aman.ethauthserver.swagger.api.AuthenticationApi;
import it.aman.ethauthserver.swagger.model.ModelLogin;
import it.aman.ethauthserver.swagger.model.ResponseModelToken;

@CrossOrigin
@RestController
public class AuthenticationController extends AbstractResponseHandler implements AuthenticationApi {

	@Autowired
	private AuthenticationService authenticationService;
	
	@Override
	@EthLoggable
	public ResponseEntity<ResponseModelToken> authenticateUser(@ApiParam(value = ""  )  @Valid @RequestBody ModelLogin login) 
	{
		ResponseModelToken response = null;
		Class<ResponseModelToken> responseClass = ResponseModelToken.class;
		HttpStatus status = HttpStatus.OK;
		try{
			ResponseModelToken model = new ResponseModelToken();
			String token = authenticationService.authenticateAndGenerateToken(login.getUserName(), login.getPassword());
			model.setTokenString(token);
			response = fillSuccessResponse(model);
		} catch (Exception ex) {
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			if(ex instanceof BadCredentialsException) {
				status = HttpStatus.UNAUTHORIZED;
			}
			response = fillResponseEthException(responseClass, ex);
		}
		
		return new ResponseEntity<>(response, status);
	}

}
