package com.rk.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.rk.model.Uom;

public class UomExcelView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// Download + filename
		response.setHeader("Content-Disposition", "attachment;filename=Uom.xlsx");

		// read data from controller
		@SuppressWarnings("unchecked")
		List<Uom> list = (List<Uom>) model.get("obs");

		Sheet sheet = workbook.createSheet("Uom Report");
		setHead(sheet);
		setBody(sheet, list);
	}

	private void setHead(Sheet sheet) {
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("TYPE");
		row.createCell(2).setCellValue("MODEL");
		row.createCell(3).setCellValue("DESCRIPTION");

	}

	private void setBody(Sheet sheet, List<Uom> list) {
		int rowNum = 1;
		for (Uom u : list) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(u.getId());
			row.createCell(1).setCellValue(u.getUomType());
			row.createCell(2).setCellValue(u.getUomModel());
			row.createCell(3).setCellValue(u.getDescription());

		}

	}
}