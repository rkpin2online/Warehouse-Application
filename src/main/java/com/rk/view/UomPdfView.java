package com.rk.view;

import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.PdfWriter;

import com.rk.model.Uom;

public class UomPdfView extends AbstractPdfView {

	@SuppressWarnings("unchecked")
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Uom> list = null;
		Paragraph p = null;
		Table t = null;
		Font font1 = null;
		// read data from controller
		list = (List<Uom>) model.get("obs");

		// set download +file
		response.setHeader("Content-Disposition", "attachment;filename=Uom.pdf");
		font1 = new Font(Font.HELVETICA, 20, Font.BOLD, new Color(130, 37, 30));

		// get paragraph object
		p = new Paragraph("Uom Report", font1);
		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);
		t = new Table(4, list.size());
		t.setSpacing(0.5f);
		t.setWidths(new float[] { 1.5f, 2.5f, 2.5f, 3.5f });
		t.setAlignment(Element.ALIGN_CENTER);
		t.setPadding(1.0f);
		setHead(t);

		setBody(document, list, t);
	}

	private void setHead(Table t) throws BadElementException {
		Font font = new Font(Font.TIMES_ROMAN, 15, Font.BOLD, Color.BLUE);
		t.addCell(new Phrase("ID", font));
		t.addCell(new Phrase("TYPE", font));
		t.addCell(new Phrase("MODEL", font));
		t.addCell(new Phrase("DESCRIPTION", font));

	}

	private void setBody(Document doc, List<Uom> list, Table t) throws BadElementException, DocumentException {
		Font font = new Font(Font.TIMES_ROMAN, 15, Font.BOLD, Color.MAGENTA);
		for (Uom u : list) {

			t.addCell(new Phrase(u.getId().toString(), font));
			t.addCell(u.getUomType());
			t.addCell(u.getUomModel());
			t.addCell(u.getDescription());
		}
		doc.add(t);
	}

}