package com.sp.ws.lambda.handler;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.sp.ws.lambda.config.AppConfig;
import com.sp.ws.lambda.router.LambdaRouter;

public class ApiHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent>{

	private static final ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
	private static final LambdaRouter router = applicationContext.getBean(LambdaRouter.class);
	
	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context) {		
		return router.route(request);
	}

}
