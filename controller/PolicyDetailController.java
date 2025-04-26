package com.kingsmen.kingsreachdev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kingsmen.kingsreachdev.entity.PolicyDetail;
import com.kingsmen.kingsreachdev.service.PolicyDetailService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@CrossOrigin(origins = {"http://hrms.kingsmenrealty.com/", "http://localhost:5173/"})
@RestController
public class PolicyDetailController {

	@Autowired
	private PolicyDetailService policyDetailService; 
	
	@PostMapping(value = "/policyDetail")
	private ResponseEntity<ResponseStructure<PolicyDetail>> policyDetail(@RequestBody PolicyDetail detail) {
		return policyDetailService.policyDetail(detail);
	}
	
	@PutMapping("/editPolicy")
	public ResponseEntity<ResponseStructure<PolicyDetail>> editPolicy(@RequestParam String policyName ,@RequestBody PolicyDetail policyDetail) {
		return policyDetailService.editPolicy(policyName, policyDetail);
	}
	
}
