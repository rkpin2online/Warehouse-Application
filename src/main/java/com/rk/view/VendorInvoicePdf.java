package com.rk.view;

import java.awt.Color;
import java.util.Date;
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

import com.rk.model.PurchaseDtl;
import com.rk.model.PurchaseOrder;

public class VendorInvoicePdf extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(
			Map<String, Object> model, 
			Document document, 
			PdfWriter writer,
			HttpServletRequest request, 
			HttpServletResponse response) 
					throws Exception {


		//read po object from model
		PurchaseOrder po = (PurchaseOrder)model.get("po");
		response.addHeader("Content-Disposition", "attachment;filename=PO-"+po.getOrderCode()+".pdf");

		Font font = new Font(Font.HELVETICA, 20, Font.BOLD, Color.BLUE);
		Paragraph p = new Paragraph("VENDOR INVOICE CODE"+po.getVendor().getUserCode()+"-"+po.getOrderCode(),font);
		p.setAlignment(Element.ALIGN_CENTER);

		//add element to document
		document.add(p);
		
		List<PurchaseDtl> dtls = po.getDtls();

		double finalCost = 
				dtls.stream()
				.mapToDouble(
						ob->ob.getQty()*ob.getPart().getBaseCost())
				.sum();

		PdfPTable table = new PdfPTable(4);

		table.addCell("VENDOR CODE");
		table.addCell(po.getVendor().getUserCode());
		table.addCell("ORDER CODE");
		table.addCell(po.getOrderCode());

		table.addCell("FINAL COST");
		table.addCell(String.valueOf(finalCost));
		table.addCell("SHIPMENT TYPE");
		table.addCell(po.getShipmentType().getShipmentCode());

		document.add(new Paragraph("HEADER DETAILS"));
		document.add(table);
		document.add(new Paragraph("ITEM DETAILS"));
		PdfPTable items = new PdfPTable(4);
		items.addCell("PART CODE");
		items.addCell("BASE COST");
		items.addCell("QTY");
		items.addCell("LINE TOTAL");

		for(PurchaseDtl dtl:dtls) {
			items.addCell(dtl.getPart().getPartCode());
			items.addCell(dtl.getPart().getBaseCost().toString());
			items.addCell(dtl.getQty().toString());
			items.addCell(String.valueOf(
					dtl.getPart().getBaseCost()
					*dtl.getQty()
					)
					);
		}
		document.add(items);
		document.add(new Paragraph(new Date().toString()));
	}

}
