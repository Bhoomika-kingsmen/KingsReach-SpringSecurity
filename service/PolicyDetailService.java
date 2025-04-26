package com.kingsmen.kingsreachdev.service;

import org.springframework.http.ResponseEntity;

import com.kingsmen.kingsreachdev.entity.PolicyDetail;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

public interface PolicyDetailService {

	ResponseEntity<ResponseStructure<PolicyDetail>> policyDetail(PolicyDetail detail);


	ResponseEntity<ResponseStructure<PolicyDetail>> editPolicy(String policyName, PolicyDetail policyDetail);

}
