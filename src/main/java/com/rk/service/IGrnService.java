package com.rk.service;

import java.util.List;
import java.util.Optional;

import com.rk.model.Grn;
import com.rk.model.GrnDtl;

public interface IGrnService {

	Integer saveGrn(Grn g);
	void updateGrn(Grn g);
	void deleteGrn(Integer id);
	Optional<Grn> getOneGrn(Integer id);
	List<Grn> getAllGrns();
	boolean isGrnExist(Integer id);
	//-----------Screen#2--------
	Integer saveGrnDtl(GrnDtl dtl);
	List<GrnDtl> getAllDtlsByGrnId(Integer grnId);
	void updateStatusByGrnDtlId(String status,Integer id);
}
