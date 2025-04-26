package com.kingsmen.kingsreachdev.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.kingsmen.kingsreachdev.entity.Asset;
import com.kingsmen.kingsreachdev.entity.Employee;
import com.kingsmen.kingsreachdev.entity.Notification;
import com.kingsmen.kingsreachdev.exception.IdNotFoundException;
import com.kingsmen.kingsreachdev.exceptions.AssetNotFoundException;
import com.kingsmen.kingsreachdev.repo.AssetRepo;
import com.kingsmen.kingsreachdev.repo.EmployeeRepo;
import com.kingsmen.kingsreachdev.repo.NotificationRepo;
import com.kingsmen.kingsreachdev.service.AssetService;
import com.kingsmen.kingsreachdev.util.ResponseStructure;

@Service
public class AssetServiceImpl implements AssetService {

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private AssetRepo assetRepo;

	@Autowired
	private NotificationRepo notificationRepo;

	@Override
	public ResponseEntity<ResponseStructure<Asset>> grantAssets(Asset asset) {
		String employeeId = asset.getEmployeeId();

		Optional<Employee> optional = employeeRepo.findByEmployeeId(employeeId);
		if (optional.isPresent()) {
			Employee employee = optional.get();
			asset.setEmployee(employee);
			Asset asset1 = assetRepo.save(asset);

			String message = asset.getAssetName() + " granted to " + asset.getEmployeeName();

			ResponseStructure<Asset> responseStructure = new ResponseStructure<Asset>();
			responseStructure.setStatusCode(HttpStatus.OK.value());
			responseStructure.setMessage(message);
			responseStructure.setData(asset1);

			//Notification code 
			Notification notify = new Notification();
			notify.setEmployeeId(asset.getEmployeeId());
			notify.setMessage(message);
			notify.setCreatedAt(LocalDateTime.now());
			notificationRepo.save(notify);

			return new ResponseEntity<ResponseStructure<Asset>>(responseStructure, HttpStatus.OK);
		} else {
			throw new AssetNotFoundException("Asset with ID " + asset.getAssetId() + " not found");
		}

	}

	@Override
	public ResponseEntity<ResponseStructure<List<Asset>>> findAllService() {
		List<Asset> asset =assetRepo.findAll();

		ResponseStructure<List<Asset>> responseStructure = new ResponseStructure<List<Asset>>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage("Asset Details Fetched successfully.");
		responseStructure.setData(asset);

		return new ResponseEntity<ResponseStructure<List<Asset>>>(responseStructure, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<ResponseStructure<Asset>> changeStatus(int id,Asset asset) {
		Asset asset2 = assetRepo.findById(id)
				.orElseThrow(() -> new IdNotFoundException("Asset with ID not found"));

		asset2.setStatus(asset.getStatus());

		Asset asset3 = assetRepo.save(asset2);

		String message = asset.getEmployeeName() + " asset detail updated successfully.";

		ResponseStructure<Asset> responseStructure = new ResponseStructure<Asset>();
		responseStructure.setStatusCode(HttpStatus.OK.value());
		responseStructure.setMessage(message);
		responseStructure.setData(asset3);

		//Notification code 
		Notification notify = new Notification();
		notify.setEmployeeId(asset.getEmployeeId());
		notify.setMessage(asset.getEmployeeName() + " asset detail updated successfully.");
		notify.setCreatedAt(LocalDateTime.now());
		notificationRepo.save(notify);

		return new ResponseEntity<ResponseStructure<Asset>>(responseStructure, HttpStatus.OK);

	}



}
