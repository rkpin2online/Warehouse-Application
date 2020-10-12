package com.rk.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rk.model.PurchaseDtl;
import com.rk.model.PurchaseOrder;
import com.rk.repo.PurchaseDtlRepo;
import com.rk.repo.PurchaseOrderRepository;
import com.rk.service.IPurchaseOrderService;

@Service
public class PurchaseOrderServiceImpl implements IPurchaseOrderService {

	@Autowired
	private PurchaseOrderRepository repo;

	@Autowired
	private PurchaseDtlRepo dtlRepo;

	@Override
	@Transactional()
	public Integer savePurchaseOrder(PurchaseOrder order) {
		return repo.save(order).getId();
	}

	@Override
	@Transactional()
	public void updatePurchaseOrder(PurchaseOrder order) {
		repo.save(order);
	}

	@Override
	@Transactional()
	public void deletePurchaseOrder(Integer id) {
		repo.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<PurchaseOrder> getOnePurchaseOrder(Integer id) {
		return repo.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PurchaseOrder> getAllPurchaseOrders() {
		return repo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public boolean isPurchaseOrderExist(Integer id) {
		return repo.existsById(id);
	}

	@Override
	public boolean isPurchaseOrderCodeExist(String orderCode) {

		return repo.getPurchaseOrderCodeCount(orderCode)>0?true:false;
	}
	@Override
	public List<Object[]> getPurchaseOrderQualityCheckCount() {
		return repo.getPurchaseOrderQualityCheckCount();
	}

	//-------------------screen#2-------
	@Override
	@Transactional
	public Integer addPartToPo(PurchaseDtl dtl) {
		return dtlRepo.save(dtl).getId();
	}

	@Override
	public List<PurchaseDtl> getPurchaseDtlWithPoId(Integer purchaseId) {
		return dtlRepo.getPurchaseDtlWithPoId(purchaseId);
	}

	@Override
	@Transactional
	public void deletePurchaseDtl(Integer id) {
		dtlRepo.deleteById(id);
	}


	@Override
	@Transactional
	public void updatePurchaseOrderStatus(String status, Integer id) {
		repo.updatePurchaseOrderStatus(status, id);
	}

	@Override
	public Integer getPurchaseDtlCountWithPoId(Integer purchaseId) {
		return dtlRepo.getPurchaseDtlCountWithPoId(purchaseId);
	}

	@Override
	public Map<Integer, String> getPoIdAndCodeByStatus(String status) {
		return repo.getPoIdAndCodeByStatus(status)
				.stream()
				.collect(Collectors.toMap(
						ob->Integer.valueOf(ob[0].toString()), 
						ob->ob[1].toString()
						));
	}
}


