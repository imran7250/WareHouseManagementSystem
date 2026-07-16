package com.nt.view;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;    
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nt.model.Part;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PartPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(
            Map<String, Object> model,
            Document document,
            PdfWriter writer,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        response.addHeader("Content-Disposition",
                "attachment; filename=parts.pdf");

        @SuppressWarnings("unchecked")
        List<Part> list = (List<Part>) model.get("list");

        Paragraph p = new Paragraph("PART DETAILS");
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(10.0f);

        document.add(p);

        PdfPTable table = new PdfPTable(9);

        table.addCell("ID");
        table.addCell("CODE");
        table.addCell("LENGTH");
        table.addCell("WIDTH");
        table.addCell("HEIGHT");
        table.addCell("COST");
        table.addCell("CURRENCY");
        table.addCell("UOM");
        table.addCell("SALE ORDER");

        for (Part part : list) {

            table.addCell(String.valueOf(part.getId()));
            table.addCell(part.getPartCode());
            table.addCell(String.valueOf(part.getPartLen()));
            table.addCell(String.valueOf(part.getPartWid()));
            table.addCell(String.valueOf(part.getPartHght()));
            table.addCell(String.valueOf(part.getPartCost()));
            table.addCell(part.getPartCurrency());

            table.addCell(
                    part.getUom() != null ? part.getUom().getUomModel() : "");

            table.addCell(
                    part.getOmSale() != null ? part.getOmSale().getOrderCode() : "");
        }

        document.add(table);
    }
}
