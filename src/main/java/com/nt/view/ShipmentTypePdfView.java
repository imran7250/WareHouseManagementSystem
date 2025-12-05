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
import com.nt.model.ShipmentType;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShipmentTypePdfView extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
		//file name
		response.addHeader("Content-Disposition","attachment;filename=SHIPMENTTYPE.pdf" );
		//read data from controller using model
		@SuppressWarnings("unchecked")
		List<ShipmentType> list=(List<ShipmentType>) model.get("list");
		
		Font titleFont=new Font(Font.HELVETICA,30,Font.BOLD,Color.BLUE);
		Paragraph title=new Paragraph("SHIPMENT TYPES",titleFont);
		title.setAlignment(Element.ALIGN_CENTER);
		
		
		Paragraph date=new Paragraph( new Date().toString());
		
		PdfPTable table= new PdfPTable(6);
		table.setSpacingBefore(10.0f);
		
		table.addCell("ID");
		table.addCell("MODE");
		table.addCell("CODE");
		table.addCell("ENABLE");
		table.addCell("GRADE");
		table.addCell("DESCRIPTION");
		
		for(ShipmentType st:list) {
			table.addCell(String.valueOf(st.getId()));
			table.addCell(st.getShipmentMode());
			table.addCell(st.getShipmentCode());
			table.addCell(st.getEnableShipment());
			table.addCell(st.getShipmentGrade());
			table.addCell(st.getDescription());
		}
		document.add(title);
		document.add(table);
		document.add(date);
		
		
	}

}
