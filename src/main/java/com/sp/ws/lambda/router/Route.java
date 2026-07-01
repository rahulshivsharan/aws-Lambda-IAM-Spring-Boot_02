package com.sp.ws.lambda.router;

import java.util.HashMap;
import java.util.Map;

import com.sp.ws.lambda.handler.RouteHandler;

public class Route {
	private final String method;
	private final String pattern;
	private final RouteHandler handler;
	
	public Route (String method, String pattern, RouteHandler handler) {
		this.method = method;
		this.pattern = pattern;
		this.handler = handler;
	}
	
	public boolean matches(String method, String path) {
		String regex = this.pattern.replaceAll("\\{[^/]+\\}", "[^/]+");
		return this.method.equalsIgnoreCase(method) && path.matches(regex);
	}

	public Map<String, String> extractPathParams(String path){
		Map<String, String> params = new HashMap<String, String>();
		
		String [] patternParts = this.pattern.split("/");
		String [] pathParts = path.split("/");
		
		for(int i = 0; i < patternParts.length; i++) {
			if(patternParts[i].startsWith("{") && patternParts[i].endsWith("}")) {
				String key = patternParts[i].replace("{", "").replace("}", "");
				params.put(key, pathParts[i]);
			}
		}
		
		return params;
	}
	
	public String getPattern() {
		return pattern;
	}

	public RouteHandler getHandler() {
		return handler;
	}
	
	
}
