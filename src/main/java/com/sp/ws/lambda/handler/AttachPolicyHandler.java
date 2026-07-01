package com.sp.ws.lambda.handler;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.ws.lambda.controller.IAMController;
import com.sp.ws.lambda.dto.AttachPolicyResponseDTO;

public class AttachPolicyHandler implements RouteHandler{
	
	private final IAMController controller;
	private final ObjectMapper mapper;
	
	public AttachPolicyHandler(IAMController controller, ObjectMapper mapper) {
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
			
			if(userName == null || userName.isEmpty()) {
				return new APIGatewayProxyResponseEvent().withStatusCode(400)
						 .withBody("{\"error\": \"userName is required in the path\" }");
			}
			
			if(request.getBody() == null || request.getBody().isEmpty()) {
				return new APIGatewayProxyResponseEvent().withStatusCode(400)
						 .withBody("{\"error\": \"request body is required\" }");
			}
			
			Map<String, String> body =  this.mapper.readValue(request.getBody(), Map.class);
			String policyArn = body.get("policyArn");
			
			if(policyArn == null || policyArn.isEmpty()) {
				return new APIGatewayProxyResponseEvent().withStatusCode(400)
						 .withBody("{\"error\": \"policyArn is required\" }");
			}
			
			AttachPolicyResponseDTO attachPolicyResponseDTO = this.controller.attachPolicyToUser(userName, policyArn);
			String attachPolicyResponse =  this.mapper.writeValueAsString(attachPolicyResponseDTO);
			
			return new APIGatewayProxyResponseEvent().withStatusCode(200)
													 .withBody(attachPolicyResponse);
		}catch(Exception e) {
			return new APIGatewayProxyResponseEvent().withStatusCode(500)
					 .withBody("{\"error\":\"" + e.getMessage() + "\"}");
		}
		
	}

}
