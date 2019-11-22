package it.aman.ethauthserver.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiParam;
import it.aman.ethauthserver.service.impl.AuthenticationService;
import it.aman.ethauthserver.swagger.api.AuthenticationApi;
import it.aman.ethauthserver.swagger.model.ModelLogin;
import it.aman.ethauthserver.swagger.model.ResponseModelToken;
import it.aman.ethauthserver.util.EthAuthserverConstants;
import it.aman.ethauthserver.util.EthAuthserverConstants.LogConstants;

@RestController
@CrossOrigin
public class AuthenticationController extends AbstractResponseHandler implements AuthenticationApi {
	private Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Override
	public ResponseEntity<ResponseModelToken> authenticateUser(
			@ApiParam(value = ""  )  @Valid @RequestBody ModelLogin login) {
		String methodName = "authenticateUser()";
		logger.info(EthAuthserverConstants.getMethodStart(), login.getUserName());
		
		ResponseModelToken response = null;
		Class<ResponseModelToken> responseClass = ResponseModelToken.class;
		try{
			ResponseModelToken model = new ResponseModelToken();
			String token = authenticationService.authenticateAndGenerateToken(login.getUserName(), login.getPassword());
			model.setTokenString(token);
			response = fillSuccessResponse(model);
		}catch (Exception ex) {
			logger.error(LogConstants.PARAMETER_2.getMessageText(), methodName, ex.getMessage());
			response = fillFailResponseEthException(responseClass, ex);
		} finally {
			logger.info(EthAuthserverConstants.getMethodEnd(), methodName);
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
