package com.nt.view;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nt.model.PurchaseDtl;
import com.nt.model.SaleDtl;
import com.nt.model.SaleOrder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomerInvoicePdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document,
			PdfWriter writer,
			HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
	    
		//header for file name
		response.addHeader("Content-Disposition", "attachment;filename=CustomerInvoice.pdf");
		
		
		//create Element
		Font font=new Font(Font.TIMES_ROMAN,22,Font.BOLD,Color.blue);
		Paragraph p=new Paragraph("CUSTOMER INVOICE PDF!!",font);
		p.setSpacingAfter(10.0f);
		p.setAlignment(Element.ALIGN_CENTER);
		//add element to document
		document.add(p);
		
		
		
		
		SaleOrder so=(SaleOrder)model.get("so");
		List<SaleDtl> dtls=(List<SaleDtl>)model.get("dtls");
		
		double finalCost=0.0;
		for(SaleDtl dtl:dtls) {
			finalCost += dtl.getPart().getPartCost() * dtl.getQty();
		}
		
		
		
		//Create table that one row contain n-column
		PdfPTable header=new PdfPTable(4);
		header.setSpacingAfter(15.0f);
		header.addCell("CUSTOMER CODE");
		header.addCell(so.getCustomer().getUserCode());
		
		header.addCell("ORDER CODE");
		header.addCell(so.getOrderCode());
		
		header.addCell("FINAL COST");
		header.addCell(String.valueOf(finalCost));
		
		header.addCell("SHIPMENT CODE");
		header.addCell(so.getShipmentType().getShipmentCode());
		
		document.add(header);
		
		PdfPTable partsTab=new PdfPTable(4);
		header.setSpacingAfter(15.0f);
		
		partsTab.addCell("CODE");
		partsTab.addCell("COST");
		partsTab.addCell("QTY");
		partsTab.addCell("VALUE");
		
		for(SaleDtl dtl:dtls) {
			partsTab.addCell(dtl.getPart().getPartCode());
			partsTab.addCell(String.valueOf(dtl.getPart().getPartCost()));
			partsTab.addCell(String.valueOf(dtl.getQty()));
			partsTab.addCell(String.valueOf(dtl.getPart().getPartCost() * dtl.getQty()));
		}
		 document.add(partsTab);
		
		document.add(new Paragraph(new Date().toString()));
	}

}
