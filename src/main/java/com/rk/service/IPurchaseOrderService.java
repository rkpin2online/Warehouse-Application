package com.rk.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.rk.model.PurchaseDtl;
import com.rk.model.PurchaseOrder;

public interface IPurchaseOrderService {

	Integer savePurchaseOrder(PurchaseOrder order);
	void updatePurchaseOrder(PurchaseOrder order);
	void deletePurchaseOrder(Integer id);
	Optional<PurchaseOrder> getOnePurchaseOrder(Integer id);
	List<PurchaseOrder> getAllPurchaseOrders();
	boolean isPurchaseOrderExist(Integer id);

	boolean isPurchaseOrderCodeExist(String orderCode); 
	List<Object[]>getPurchaseOrderQualityCheckCount();

	//Screen#2 Operations
	Integer addPartToPo(PurchaseDtl  dtl);
	List<PurchaseDtl> getPurchaseDtlWithPoId(Integer purchaseId);
	void deletePurchaseDtl(Integer id);
	void updatePurchaseOrderStatus(String status,Integer id);
	Integer getPurchaseDtlCountWithPoId(Integer purchaseId);
	Map<Integer,String> getPoIdAndCodeByStatus(String status);
}
