package com.sp.ws.lambda.router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.sp.ws.lambda.handler.RouteHandler;

public class LambdaRouter {
	
	private final List<Route> routes = new ArrayList<Route>();
	
	public void register(String method, String path, RouteHandler handler) {
		routes.add(new Route(method, path, handler));		
	}
	
	
	
	public APIGatewayProxyResponseEvent route(APIGatewayProxyRequestEvent request) {
		String rawPath = request.getPath();
		String method = request.getHttpMethod();
		
		
		if(rawPath == null || method == null) {
			return new APIGatewayProxyResponseEvent().withStatusCode(404).withBody("{ \"error\": \"Route not found\" }"); 
		}
		
		String path = rawPath.replaceFirst("^/iamLambdaSpringBootDeploy", "");
		
		
		for(Route route : this.routes) {
			
			if(route.matches(method, path)) {
				
				Map<String, String> pathParams = route.extractPathParams(path);
				request.setPathParameters(pathParams);
				return route.getHandler().handle(request);
			}
		}
		
		
		
		return new APIGatewayProxyResponseEvent().withStatusCode(404).withBody("{ \"error\": \"Route not found\" }");
	}
	
	
}
