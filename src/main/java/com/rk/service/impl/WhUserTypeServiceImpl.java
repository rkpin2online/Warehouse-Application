package com.rk.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rk.model.WhUserType;
import com.rk.repo.WhUserTypeRepository;
import com.rk.service.IWhUserTypeService;

@Service
public class WhUserTypeServiceImpl implements IWhUserTypeService {

	@Autowired
	private WhUserTypeRepository repo;

	@Transactional
	public Integer saveWhUserType(WhUserType user) {
		return repo.save(user).getId();
	}

	@Transactional
	public void updateWhUserType(WhUserType user) {
		repo.save(user);

	}

	@Override
	@Transactional
	public void deleteWhUserType(Integer id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional
	public Optional<WhUserType> getOneWhUserType(Integer id) {
		return repo.findById(id);
	}

	@Override
	@Transactional
	public List<WhUserType> getAllWhUserTypes() {
		return repo.findAll();
	}

	@Override
	@Transactional
	public boolean isWhUserTypeExist(Integer id) {
		return repo.existsById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isWhUserTypeEmailExist(String mail) {
		return repo.getWhUserTypeCount(mail)>0?true:false;
	}

	@Override
	@Transactional(readOnly = true)
	public Map<Integer, String> getWhUserTypeIdAndCode(String userType) {
		return repo.getWhUserTypeIdAndCode(userType)
				.stream()
				.collect(Collectors.toMap(
						ob->Integer.valueOf(ob[0].toString()),
						ob->ob[1].toString())
						);
	}

}
