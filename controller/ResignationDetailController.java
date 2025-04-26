package com.kingsmen.kingsreachdev.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kingsmen.kingsreachdev.entity.ResignationDetail;
import com.kingsmen.kingsreachdev.service.ResignationDetailService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@CrossOrigin(origins = {"http://hrms.kingsmenrealty.com/", "http://localhost:5173/"})
@RestController
public class ResignationDetailController {
	
	@Autowired
	private ResignationDetailService resignationDetailService; 

	@PostMapping(value = "/resignationDetail")
	private ResponseEntity<ResponseStructure<ResignationDetail>> resignationDetail(@RequestBody ResignationDetail resignationDetail) {
		return resignationDetailService.resignationDetail(resignationDetail);
	}
	
	@GetMapping(value = "/getResignationDetails")
	private ResponseEntity<ResponseStructure<List<ResignationDetail>>> getResignationDetails(){
		return resignationDetailService.getResignationDetails();
	}
	
	@PutMapping(value = "/editResignationStatus/{resignationId}")
	private ResponseEntity<ResponseStructure<ResignationDetail>> changeResignationStatus(@PathVariable int resignationId, @RequestBody ResignationDetail resignationDetail){
		return resignationDetailService.changeResignationStatus(resignationId , resignationDetail);
	}
	
	@GetMapping(value = "/getAllDetails")
	private ResponseEntity<ResponseStructure<Map<String, Object>>> getAllDetails(){
		return resignationDetailService.getAllDetails();
	}
}
