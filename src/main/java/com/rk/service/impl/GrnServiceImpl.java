package com.rk.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rk.model.Grn;
import com.rk.model.GrnDtl;
import com.rk.repo.GrnDtlRepo;
import com.rk.repo.GrnRepo;
import com.rk.service.IGrnService;

@Service
public class GrnServiceImpl implements IGrnService {

	@Autowired
	private GrnRepo repo;
	@Autowired
	private GrnDtlRepo dtlRepo;

	@Override
	public Integer saveGrn(Grn g) {
		return repo.save(g).getId();
	}

	@Override
	public void updateGrn(Grn g) {
		repo.save(g);
	}

	@Override
	public void deleteGrn(Integer id) {
		repo.deleteById(id);
	}

	@Override
	public Optional<Grn> getOneGrn(Integer id) {
		return repo.findById(id);
	}

	@Override
	public List<Grn> getAllGrns() {
		return repo.findAll();
	}

	@Override
	public boolean isGrnExist(Integer id) {
		return repo.existsById(id);
	}

	@Override
	public Integer saveGrnDtl(GrnDtl dtl) {
		return dtlRepo.save(dtl).getId();
	}

	@Override
	public List<GrnDtl> getAllDtlsByGrnId(Integer grnId) {
		return dtlRepo.getAllDtlsByGrnId(grnId);
	}
	
	@Override
	@Transactional
	public void updateStatusByGrnDtlId(String status, Integer id) {
		dtlRepo.updateStatusByGrnDtlId(status, id);
	}
}
