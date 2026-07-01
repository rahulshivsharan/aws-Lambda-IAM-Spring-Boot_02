package com.sp.ws.lambda.controller;

import java.util.List;

import com.sp.ws.lambda.dto.AttachPolicyResponseDTO;
import com.sp.ws.lambda.dto.IAMAccessKeyDTO;
import com.sp.ws.lambda.dto.IAMPolicyDTO;
import com.sp.ws.lambda.dto.IAMUserDTO;
import com.sp.ws.lambda.service.IAMService;

public class IAMController {
	private final IAMService iamService;
	
	public IAMController(IAMService iamService) {
		this.iamService = iamService;
	}
	
	public List<IAMUserDTO> listUsers(){
		return this.iamService.listUsers();
	}
	
	public List<IAMPolicyDTO> listUserPolicies(String userName){
		return this.iamService.listUserPolicies(userName);
	}
	
	public List<IAMAccessKeyDTO> listAccessKeys(String userName){
		return this.iamService.listAccessKeys(userName);
	}
	
	public IAMUserDTO createUser(String userName){
		return this.iamService.createUser(userName);
	}
	
	public AttachPolicyResponseDTO attachPolicyToUser(String userName, String policyArn){
		return this.iamService.attachPolicyToUser(userName, policyArn);
	}
}
