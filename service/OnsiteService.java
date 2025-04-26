package com.kingsmen.kingsreachdev.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.kingsmen.kingsreachdev.entity.Onsite;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

public interface OnsiteService {

	ResponseEntity<ResponseStructure<Onsite>> onsiteEmployee(Onsite onsite);

	ResponseEntity<ResponseStructure<List<Onsite>>> findOnsiteEmployees();

	ResponseEntity<ResponseStructure<Onsite>> getOnsiteEmployee(String employeeId);

}
