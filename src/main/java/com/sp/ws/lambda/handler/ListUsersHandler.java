package com.sp.ws.lambda.handler;

import java.util.List;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.ws.lambda.controller.IAMController;
import com.sp.ws.lambda.dto.IAMUserDTO;

public class ListUsersHandler implements RouteHandler{

	private final IAMController controller;
    private final ObjectMapper mapper;
    
    public ListUsersHandler(IAMController controller, ObjectMapper mapper) {
        this.controller = controller;
        this.mapper = mapper;
    }
	
	@Override
	public APIGatewayProxyResponseEvent handle(APIGatewayProxyRequestEvent request) {
		try {			
			List<IAMUserDTO> userList = controller.listUsers();
			String userResponse = mapper.writeValueAsString(userList);
            return new APIGatewayProxyResponseEvent().withStatusCode(200)
                    								 .withBody(userResponse);

        } catch (Exception e) {
            return new APIGatewayProxyResponseEvent().withStatusCode(500)
                    								 .withBody("{\"error\":\"" + e.getMessage() + "\"}");
        }
	}

}
