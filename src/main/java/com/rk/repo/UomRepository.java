package com.rk.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.rk.model.Uom;

public interface UomRepository extends JpaRepository<Uom, Integer> {

	@Query("SELECT id,uomModel FROM Uom")
	List<Object[]> getUomIdAndModel();

	@Query("SELECT count(id) FROM Uom where uomModel=:model")
	int getUomModelCount(String model);
}