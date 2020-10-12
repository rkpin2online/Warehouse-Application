package com.rk.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.rk.model.Part;

public class PartExcelView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//Download + filename
				response.setHeader("Content-Disposition", "attachment;filename=Part.xlsx");
				
				//read data from controller
				@SuppressWarnings("unchecked")
				List<Part>list =(List<Part>) model.get("obs");
				
				Sheet sheet=workbook.createSheet("Part ");
				setHead(sheet);
				setBody(sheet,list);
			}


			private void setHead(Sheet sheet) {
				Row row = sheet.createRow(0);
				row.createCell(0).setCellValue("ID");
				row.createCell(1).setCellValue("PARTCODE");
				row.createCell(2).setCellValue("PARTWIDTH");
				row.createCell(3).setCellValue("PARTLEN");
				row.createCell(4).setCellValue("PARTHGH");
				row.createCell(5).setCellValue("BASECOST");				
				row.createCell(6).setCellValue("BASECURR");				
				row.createCell(7).setCellValue("DESCRIPTION");				
				row.createCell(8).setCellValue("UOM");				
			}

			private void setBody(Sheet sheet, List<Part> list) {
				int rowNum=1;
				for(Part p:list) {
					Row row = sheet.createRow(rowNum++);
					row.createCell(0).setCellValue(p.getId());
					row.createCell(1).setCellValue(p.getPartCode());
					row.createCell(2).setCellValue(p.getPartWidth());
					row.createCell(3).setCellValue(p.getPartLen());
					row.createCell(4).setCellValue(p.getPartHgh());
					row.createCell(5).setCellValue(p.getBaseCost());
					row.createCell(6).setCellValue(p.getBaseCurr());
					row.createCell(7).setCellValue(p.getDescription());
					row.createCell(8).setCellValue(p.getUom().getUomModel());
					
				}

	}

}