package com.rk.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import com.rk.model.ShipmentType;

public class ShipmentTypePdfView extends AbstractPdfView {
	
	@Override
	protected void buildPdfDocument(
			Map<String, Object> model, 
			Document document, 
			PdfWriter writer,
			HttpServletRequest request,
			HttpServletResponse response) 
					throws Exception 
	{
		
		//download document
		response.addHeader("Content-Disposition", "attachment;filename=shipments.pdf");
		
		
		//create element paragraph
		Font font = new Font(Font.HELVETICA, 20, Font.BOLD, Color.BLUE);
		Paragraph p = new Paragraph("WELCOME TO SHIPMENT TYPE",font);
		p.setAlignment(Element.ALIGN_CENTER);

		//add element to document
		document.add(p);


		//reading data
		@SuppressWarnings("unchecked")
		List<ShipmentType> list=(List<ShipmentType>) model.get("list");

		PdfPTable table = new PdfPTable(6); //no of columns
		table.setSpacingBefore(4.0f);
		table.setTotalWidth(new float[] {1.0f,1.0f,1.5f,1.0f,1.0f,1.5f});

		table.addCell("ID");
		table.addCell("MODE");
		table.addCell("CODE");
		table.addCell("ENABLE");
		table.addCell("GRADE");
		table.addCell("DESCRIPTION");

		for(ShipmentType st:list) {
			table.addCell(st.getId().toString());
			table.addCell(st.getShipmentMode());
			table.addCell(st.getShipmentCode());
			table.addCell(st.getEnableShipment());
			table.addCell(st.getShipmentGrade());
			table.addCell(st.getDescription());
		}


		document.add(table);
	}
}








