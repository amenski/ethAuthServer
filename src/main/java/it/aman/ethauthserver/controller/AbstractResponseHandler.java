package it.aman.ethauthserver.controller;

import it.aman.ethauthserver.swagger.model.ResponseBase;

public abstract class AbstractResponseHandler {

	@SuppressWarnings("unchecked")
	public <T extends ResponseBase> T fillSuccessResponse(T response) {
		if (response == null) {
			response = (T) getNewInstance(response.getClass());
		}
		response.success(true);
		response.resultCode(200);
		response.message("");
		response.errors(null);

		return response;
	}

	public <T extends ResponseBase> T fillFailResponseEthException(Class<T> response, Exception e) {
		T res = getNewInstance(response);
		res.success(false);
//		res.resultCode(e.getInternalCode());
		res.message(e.getMessage() != null ? e.getMessage() : "");
//		res.errors(e.getErrors());

		return res;
	}

	public <T extends ResponseBase> T fillFailResponseGeneric(Class<T> response1) {
		T response = getNewInstance(response1);
		response.success(false);
		// response.resultCode(EthExceptionEnums.UNHANDLED_EXCEPTION.get().getInternalCode());
		// response.message(EthExceptionEnums.UNHANDLED_EXCEPTION.get().getMessage());
		response.errors(null);

		return response;
	}

	@SuppressWarnings("unchecked")
	private <T> T getNewInstance(Class<T> clazz) {
		T newInstance;
		try {
			newInstance = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			newInstance = (T) new ResponseBase();
		}
		return newInstance;
	}
}
