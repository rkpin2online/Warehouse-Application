package com.rk.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.rk.model.OrderMethod;

public class OrderMethodExcelView extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(
			Map<String, Object> model, 
			Workbook workbook, 
			HttpServletRequest request,
			HttpServletResponse response)
					throws Exception {

		//file name
		response.addHeader("Content-Disposition", "attachment;filename=ordermethods.xlsx");

		//read data given by controller
		@SuppressWarnings("unchecked")
		List<OrderMethod> list =  (List<OrderMethod>) model.get("list");
		
		//create new sheet
		Sheet sheet=workbook.createSheet("ORDER METHODS");
		setHead(sheet);
		setBody(sheet, list);

	}

	private void setHead(Sheet sheet) {
		//create row
		Row row=sheet.createRow(0);
		//create cell
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("ORDER MODE");
		row.createCell(2).setCellValue("ORDER CODE");
		row.createCell(3).setCellValue("ORDER TYPE");
		row.createCell(4).setCellValue("ORDER ACPT");
		row.createCell(5).setCellValue("DESCRIPTION");
	}



	private void setBody(Sheet sheet, List<OrderMethod> list) {
		int rownum=1;
		for(OrderMethod om:list) {
			Row row=sheet.createRow(rownum);
			
			row.createCell(0).setCellValue(om.getId());
			row.createCell(1).setCellValue(om.getOrderCode());
			row.createCell(2).setCellValue(om.getOrderMode());
			row.createCell(3).setCellValue(om.getOrderType());
			row.createCell(4).setCellValue(om.getOrderAcpt().toString());
			row.createCell(5).setCellValue(om.getDescription());
			
			rownum++;
		}
	}
	
	
	
	
}
