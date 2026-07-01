package com.sp.ws.lambda.dto;


public class IAMUserDTO {
	
	private String userName;
	private String arn;
	private String userId;
	
	public IAMUserDTO(String userName, String arn, String userId) {
		this.userName = userName;
		this.arn = arn;
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getArn() {
		return arn;
	}
	
	public String getUserId() {
		return userId;
	}
	
	
	
}
