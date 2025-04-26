package com.kingsmen.kingsreachdev.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kingsmen.kingsreachdev.entity.PolicyDetail;

public interface PolicyDetailRepo extends JpaRepository<PolicyDetail, Integer>{

	Optional<PolicyDetail> findByPolicyName(String policyName);

}
