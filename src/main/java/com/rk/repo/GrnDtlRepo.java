package com.rk.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.rk.model.GrnDtl;

public interface GrnDtlRepo extends JpaRepository<GrnDtl, Integer>{

	@Query("SELECT dtl FROM GrnDtl dtl INNER JOIN dtl.grn AS grn WHERE grn.id=:grnId")
	public List<GrnDtl> getAllDtlsByGrnId(Integer grnId);
	
	
	//@Query + @Modifying ==> non- select
	@Modifying
	@Query("UPDATE GrnDtl set status=:status WHERE id=:id")
	void updateStatusByGrnDtlId(String status,Integer id);
}
