package com.sp.ws.lambda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sp.ws.lambda.controller.IAMController;
import com.sp.ws.lambda.handler.AttachPolicyHandler;
import com.sp.ws.lambda.handler.CreateUserHandler;
import com.sp.ws.lambda.handler.ListAccessKeysHandler;
import com.sp.ws.lambda.handler.ListUserPoliciesHandler;
import com.sp.ws.lambda.handler.ListUsersHandler;
import com.sp.ws.lambda.router.LambdaRouter;
import com.sp.ws.lambda.service.IAMService;

@Configuration
public class AppConfig {
	
	@Bean
	public IAMService iamService() {
		return new IAMService();
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	
	
	@Bean
	public IAMController iamController(IAMService iamService) {
		return new IAMController(iamService);
	}
	
	
	@Bean
	public ListUsersHandler listUsersHandler(IAMController controller, ObjectMapper objectMapper) {
		return new ListUsersHandler(controller, objectMapper);
	}
	
	
	@Bean
	public ListUserPoliciesHandler listUserPoliciesHandler(IAMController controller, ObjectMapper objectMapper) {
		return new ListUserPoliciesHandler(controller, objectMapper);
	}
	
	
	@Bean
	public ListAccessKeysHandler listAccessKeysHandler(IAMController controller, ObjectMapper objectMapper) {
		return new ListAccessKeysHandler(controller, objectMapper);
	}
	
	
	@Bean
	public CreateUserHandler createUserHandler(IAMController controller, ObjectMapper objectMapper) {
		return new CreateUserHandler(controller, objectMapper);
	}
	
	@Bean
	public AttachPolicyHandler attachPolicyHandler(IAMController controller, ObjectMapper objectMapper) {
		return new AttachPolicyHandler(controller, objectMapper);
	}
	
	
	@Bean
	public LambdaRouter lambdaRouter(ListUsersHandler listUsersHandler, 
										ListAccessKeysHandler listAccessKeysHandler,	
										ListUserPoliciesHandler listUserPoliciesHandler,
										CreateUserHandler createUserHandler,
										AttachPolicyHandler attachPolicyHandler) {
		LambdaRouter router = new LambdaRouter();
		router.register("GET", "/users", listUsersHandler);
		router.register("POST", "/users/{userName}", createUserHandler);
		router.register("GET", "/users/{userName}/keys", listAccessKeysHandler);
		router.register("GET", "/users/{userName}/policy", listUserPoliciesHandler);
		router.register("POST", "/users/{userName}/policy", attachPolicyHandler);
		return router;
	}
	
}
