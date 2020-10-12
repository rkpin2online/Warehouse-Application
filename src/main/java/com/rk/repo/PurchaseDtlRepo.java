package com.rk.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rk.model.PurchaseDtl;

public interface PurchaseDtlRepo extends JpaRepository<PurchaseDtl, Integer> {

	@Query("SELECT PDTL FROM PurchaseDtl PDTL INNER JOIN PDTL.po AS PO WHERE PO.id=:purchaseId")
	public List<PurchaseDtl> getPurchaseDtlWithPoId(Integer purchaseId);
	
	@Query("SELECT count(PDTL.id) FROM PurchaseDtl PDTL INNER JOIN PDTL.po AS PO WHERE PO.id=:purchaseId")
	public Integer getPurchaseDtlCountWithPoId(Integer purchaseId);
}
