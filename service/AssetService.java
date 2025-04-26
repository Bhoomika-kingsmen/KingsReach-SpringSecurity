package com.kingsmen.kingsreachdev.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.kingsmen.kingsreachdev.entity.Asset;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

public interface AssetService {

	ResponseEntity<ResponseStructure<Asset>> grantAssets(Asset asset);

	ResponseEntity<ResponseStructure<List<Asset>>> findAllService();

	ResponseEntity<ResponseStructure<Asset>> changeStatus(int id, Asset asset);

}
