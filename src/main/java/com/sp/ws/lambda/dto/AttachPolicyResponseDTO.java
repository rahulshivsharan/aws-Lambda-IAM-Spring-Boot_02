package com.sp.ws.lambda.dto;

public class AttachPolicyResponseDTO {
	
	private String userName;
	private String policyArn;
	private String status;
	private String message;
	
	public AttachPolicyResponseDTO(String userName, String policyArn, String status, String message) {
		this.userName = userName;
		this.policyArn = policyArn;
		this.status = status;
		this.message = message;
	}

	public String getUserName() {
		return userName;
	}

	public String getPolicyArn() {
		return policyArn;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}
	
	
}
