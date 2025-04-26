package com.kingsmen.kingsreachdev.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.kingsmen.kingsreachdev.entity.ResignationDetail;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

public interface ResignationDetailService {

	ResponseEntity<ResponseStructure<ResignationDetail>> resignationDetail(ResignationDetail resignationDetail);

	ResponseEntity<ResponseStructure<List<ResignationDetail>>> getResignationDetails();

	ResponseEntity<ResponseStructure<ResignationDetail>> changeResignationStatus(int resignationId,ResignationDetail resignationDetail);

	ResponseEntity<ResponseStructure<Map<String, Object>>> getAllDetails();
}
