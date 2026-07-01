package com.sp.ws.lambda.dto;

public class IAMAccessKeyDTO {
	private String accessKeyId;
	private String status;
	
	
	public IAMAccessKeyDTO(String accessKeyId, String status) {
		this.accessKeyId = accessKeyId;
		this.status = status;
	}


	public String getAccessKeyId() {
		return accessKeyId;
	}


	public String getStatus() {
		return status;
	}

	
}
