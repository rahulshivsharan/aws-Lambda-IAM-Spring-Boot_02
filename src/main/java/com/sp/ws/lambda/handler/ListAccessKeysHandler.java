package com.sp.ws.lambda.handler;

import java.util.List;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.ws.lambda.controller.IAMController;
import com.sp.ws.lambda.dto.IAMAccessKeyDTO;

public class ListAccessKeysHandler implements RouteHandler{

	private final IAMController controller;
    private final ObjectMapper mapper;
    
    public ListAccessKeysHandler(IAMController controller, ObjectMapper mapper) {
    	this.controller = controller;
        this.mapper = mapper;
    }
	
	@Override
	public APIGatewayProxyResponseEvent handle(APIGatewayProxyRequestEvent request) {
		try {
			String userName = null;
			if(request.getPathParameters() != null && request.getPathParameters().get("userName") != null) {
				userName = request.getPathParameters().get("userName");
			}
			
			List<IAMAccessKeyDTO> accessKeyList = controller.listAccessKeys(userName);
			
			String userAccessKeysResponse = mapper.writeValueAsString(accessKeyList);
            return new APIGatewayProxyResponseEvent().withStatusCode(200)
                    								 .withBody(userAccessKeysResponse);
            
		}catch(Exception e) {
			return new APIGatewayProxyResponseEvent().withStatusCode(500)
					 .withBody("{\"error\":\"" + e.getMessage() + "\"}");
		}
		
	}

}
