package com.kingsmen.kingsreachdev.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.kingsmen.kingsreachdev.entity.TerminationDetail;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

public interface TerminationDetailService {

	ResponseEntity<ResponseStructure<TerminationDetail>> terminationDetail(TerminationDetail detail);
	
	ResponseEntity<ResponseStructure<TerminationDetail>> editTermination(int terminationDetailId,TerminationDetail terminationDetail);

	ResponseEntity<ResponseStructure<TerminationDetail>> deleteTermination(int terminationDetailId);

	ResponseEntity<ResponseStructure<List<TerminationDetail>>> findAllTerminations();


}
