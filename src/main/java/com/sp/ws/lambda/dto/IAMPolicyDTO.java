package com.sp.ws.lambda.dto;

public class IAMPolicyDTO {
	private String policyName;
	private String policyArn;
	
	public IAMPolicyDTO(String policyName, String policyArn) {
		this.policyArn = policyArn;
		this.policyName = policyName;
	}
	
	public String getPolicyArn() {
		return policyArn;
	}
	
	public void setPolicyArn(String policyArn) {
		this.policyArn = policyArn;
	}
	
	
}
