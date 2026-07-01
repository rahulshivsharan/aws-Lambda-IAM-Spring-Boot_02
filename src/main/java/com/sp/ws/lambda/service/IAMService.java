package com.sp.ws.lambda.service;

import java.util.List;
import java.util.stream.Collectors;

import com.sp.ws.lambda.dto.AttachPolicyResponseDTO;
import com.sp.ws.lambda.dto.IAMAccessKeyDTO;
import com.sp.ws.lambda.dto.IAMPolicyDTO;
import com.sp.ws.lambda.dto.IAMUserDTO;
import com.sp.ws.lambda.mapper.IAMMapper;

import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.iam.IamClient;
import software.amazon.awssdk.services.iam.model.AttachUserPolicyRequest;
import software.amazon.awssdk.services.iam.model.AttachUserPolicyResponse;
import software.amazon.awssdk.services.iam.model.CreateUserRequest;
import software.amazon.awssdk.services.iam.model.CreateUserResponse;
import software.amazon.awssdk.services.iam.model.IamRequest;
import software.amazon.awssdk.services.iam.model.ListAccessKeysRequest;
import software.amazon.awssdk.services.iam.model.ListAccessKeysResponse;
import software.amazon.awssdk.services.iam.model.ListAttachedUserPoliciesRequest;
import software.amazon.awssdk.services.iam.model.ListAttachedUserPoliciesResponse;
import software.amazon.awssdk.services.iam.model.ListUsersResponse;
import software.amazon.awssdk.services.iam.model.NoSuchEntityException;
import software.amazon.awssdk.services.iam.model.User;

public class IAMService {
	private final IamClient iamClient = IamClient.builder()
												 .region(Region.AWS_GLOBAL)
												 .httpClientBuilder(UrlConnectionHttpClient.builder())
												 .build();
	
	public List<IAMUserDTO> listUsers() {
		ListUsersResponse userResponse = iamClient.listUsers();
		List<User> users = userResponse.users();
		
		List<IAMUserDTO> userList = users.stream()
										 .map(IAMMapper::toUserDTO)
										 .collect(Collectors.toList());		
		return userList;
	}
	
	
	public List<IAMPolicyDTO> listUserPolicies(String username){
		ListAttachedUserPoliciesRequest userPolicyRequest = ListAttachedUserPoliciesRequest.builder()
																						   .userName(username)
																						   .build();
		
		ListAttachedUserPoliciesResponse userPolicyResponse =  iamClient.listAttachedUserPolicies(userPolicyRequest);
		
		List<IAMPolicyDTO> policyList = userPolicyResponse.attachedPolicies()
														  .stream()
														  .map(IAMMapper::toPolicyDTO)
														  .collect(Collectors.toList()); 


		return policyList;
	}
	
	
	public List<IAMAccessKeyDTO> listAccessKeys(String username){
		ListAccessKeysRequest listAccessKeysRequest = ListAccessKeysRequest.builder()
																		    .userName(username)
																			.build();
		
	 	ListAccessKeysResponse accessKeysResponse = iamClient.listAccessKeys(listAccessKeysRequest);
	 	
	 	List<IAMAccessKeyDTO> accessKeyList = accessKeysResponse.accessKeyMetadata()
																.stream()
																.map(IAMMapper::toAccessKeyDTO)
																.collect(Collectors.toList());

	 	return accessKeyList;
	}
	
	
	public AttachPolicyResponseDTO attachPolicyToUser(String username, String policyArn) {
		try {
			AttachUserPolicyRequest request = AttachUserPolicyRequest.builder()
					 .userName(username)
					 .policyArn(policyArn)
					 .build();
			
			AttachUserPolicyResponse response = iamClient.attachUserPolicy(request);

			return new AttachPolicyResponseDTO(username, policyArn, "SUCCESS", "Policy attached successfully");
		}catch(NoSuchEntityException ex) {
			return new AttachPolicyResponseDTO(username, policyArn, "FAILED", "User or policy does not exist");
		}catch(Exception e) {
			return new AttachPolicyResponseDTO(username, policyArn, "ERROR", e.getMessage());
		}
	}
	
	
	public IAMUserDTO createUser(String username) {	
		IamRequest iamRequest = CreateUserRequest.builder().userName(username).build();
		CreateUserRequest createUserRequest = (CreateUserRequest) iamRequest;
		CreateUserResponse createUserResponse = iamClient.createUser(createUserRequest);
		User user = createUserResponse.user();
		
		IAMUserDTO userDTO = new IAMUserDTO(user.userName(), 
											user.arn(), 
											user.userId());
		
		return userDTO;
	}
	
	
}
