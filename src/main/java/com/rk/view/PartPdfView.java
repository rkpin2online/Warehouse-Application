package com.rk.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

import com.rk.model.Part;

public class PartPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		//read data from controller
		@SuppressWarnings("unchecked")
		List<Part> list=(List<Part>) model.get("obs");
		
		response.setHeader("Content-Disposition", "attachment;filename=Part.pdf");
		
		
		Paragraph p1 = new Paragraph("Part Report", new Font(Font.BOLD));
		document.add(p1);
		Table t= new Table(9,list.size());
		setHead( t);
		setBody(document, list, t);
		
	}
	private void setHead(Table t) throws BadElementException {

		t.addCell("ID");
		t.addCell("CODE");
		t.addCell("WIDTH");
		t.addCell("LEN");
		t.addCell("HGH");
		t.addCell("COST");
		t.addCell("CURR");
		t.addCell("DESC");
		t.addCell("UOM");

	}
	private void setBody(Document document, List<Part> list, Table t) throws BadElementException, DocumentException {
		for(Part p:list) {
			t.addCell(String.valueOf(p.getId()));
			t.addCell(p.getPartCode());
			t.addCell(p.getPartWidth());
			t.addCell(p.getPartLen());
			t.addCell(p.getPartHgh());
			t.addCell(p.getBaseCost().toString());
			t.addCell(p.getBaseCurr());
			t.addCell(p.getDescription());
			t.addCell(p.getUom().getUomModel());
		}
		document.add(t);
	}
}