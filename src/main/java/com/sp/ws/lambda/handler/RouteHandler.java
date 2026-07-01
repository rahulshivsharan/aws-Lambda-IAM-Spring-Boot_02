package com.sp.ws.lambda.handler;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public interface RouteHandler {
	public APIGatewayProxyResponseEvent handle(APIGatewayProxyRequestEvent request);
}
