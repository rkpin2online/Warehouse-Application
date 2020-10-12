package com.rk.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rk.model.Part;

public interface PartRepository extends JpaRepository<Part, Integer> {

	@Query("SELECT P.id,P.partCode FROM Part P")
	public List<Object[]> getPartIdAndCode();

	@Query("SELECT COUNT(P.partCode) FROM Part P WHERE P.partCode=:code")
	public Integer getPartCodeCount(String code);

	@Query("SELECT COUNT(P.partCode) FROM Part P WHERE P.partCode=:code and P.id!=:id")
	public Integer getPartCodeCountForEdit(String code,Integer id);
}