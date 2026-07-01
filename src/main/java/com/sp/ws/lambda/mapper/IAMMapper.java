package com.sp.ws.lambda.mapper;

import com.sp.ws.lambda.dto.IAMAccessKeyDTO;
import com.sp.ws.lambda.dto.IAMPolicyDTO;
import com.sp.ws.lambda.dto.IAMUserDTO;

import software.amazon.awssdk.services.iam.model.AccessKeyMetadata;
import software.amazon.awssdk.services.iam.model.AttachedPolicy;
import software.amazon.awssdk.services.iam.model.User;

public class IAMMapper {
	
	public static IAMUserDTO toUserDTO(User user) {
		return new IAMUserDTO(user.userName(), user.arn(), user.userId());
	}
	
	public static IAMPolicyDTO toPolicyDTO(AttachedPolicy policy) {
		return new IAMPolicyDTO(policy.policyName(), policy.policyArn());
	}
	
	
	public static IAMAccessKeyDTO toAccessKeyDTO(AccessKeyMetadata key) {
		return new IAMAccessKeyDTO(key.accessKeyId(), key.statusAsString());
	}
}
