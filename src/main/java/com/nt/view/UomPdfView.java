package com.nt.view;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nt.model.Uom;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UomPdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		response.addHeader("Content-Disposition", "attachment; filename=uom.pdf");
		//read data from model
		List<Uom>list=(List<Uom>) model.get("list");
		
		//create Element
		Paragraph p=new Paragraph("HELLO USER!!");
		p.setSpacingAfter(10.0f);
		p.setAlignment(Element.ALIGN_CENTER);
		//add element to document
		document.add(p);
		
		//create table that one row should content -n columns
		PdfPTable table=new PdfPTable(4);
		table.addCell("ID");
		table.addCell("TYPE");
		table.addCell("MODEL");
		table.addCell("DESCRIPTION");
		
		for(Uom uom:list) {
			table.addCell(String.valueOf(uom.getId()));
			//table.addCell(uom.getId().toString());
			table.addCell(uom.getUomType());
			table.addCell(uom.getUomModel());
			table.addCell(uom.getDescription());
		}
		document.add(table);
	}    

}
