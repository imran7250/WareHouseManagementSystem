package com.nt.view;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nt.model.OrderMethod;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class OrderMethodPdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(
            Map<String, Object> model,
            Document document,
            PdfWriter writer,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        response.addHeader("Content-Disposition",
                "attachment;filename=OrderMethods.pdf");

        Font font = new Font(Font.HELVETICA, 18, Font.BOLD);

        document.add(new Paragraph("ORDER METHOD DETAILS", font));

        PdfPTable table = new PdfPTable(6);

        table.setSpacingBefore(10);

        table.addCell(new PdfPCell(new Phrase("ID")));
        table.addCell(new PdfPCell(new Phrase("MODE")));
        table.addCell(new PdfPCell(new Phrase("CODE")));
        table.addCell(new PdfPCell(new Phrase("TYPE")));
        table.addCell(new PdfPCell(new Phrase("ACCEPT")));
        table.addCell(new PdfPCell(new Phrase("DESCRIPTION")));

        @SuppressWarnings("unchecked")
        List<OrderMethod> list =
                (List<OrderMethod>) model.get("list");

        for (OrderMethod om : list) {

            table.addCell(String.valueOf(om.getId()));
            table.addCell(om.getOrderMode());
            table.addCell(om.getOrderCode());
            table.addCell(om.getOrderType());
            table.addCell(String.join(",", om.getOrderAcpt()));
            table.addCell(om.getDescription());
        }

        document.add(table);
    }
}
