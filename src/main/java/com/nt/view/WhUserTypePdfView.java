package com.nt.view;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nt.model.WhUserType;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WhUserTypePdfView extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(
            Map<String, Object> model,
            Document document,
            PdfWriter writer,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        response.addHeader(
                "Content-Disposition",
                "attachment; filename=WhUserType.pdf");

        @SuppressWarnings("unchecked")
        List<WhUserType> list =
                (List<WhUserType>) model.get("list");

        Paragraph p = new Paragraph("WAREHOUSE USER TYPE DETAILS");

        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(10);

        document.add(p);

        PdfPTable table = new PdfPTable(9);

        table.addCell("ID");
        table.addCell("USER TYPE");
        table.addCell("USER CODE");
        table.addCell("USER FOR");
        table.addCell("EMAIL");
        table.addCell("CONTACT");
        table.addCell("ID TYPE");
        table.addCell("IF OTHER");
        table.addCell("ID NUMBER");

        for (WhUserType wh : list) {

            table.addCell(String.valueOf(wh.getId()));
            table.addCell(wh.getUserType());
            table.addCell(wh.getUserCode());
            table.addCell(wh.getUserFor());
            table.addCell(wh.getUserEmail());
            table.addCell(wh.getUserContact());
            table.addCell(wh.getUserIdType());
            table.addCell(wh.getIfOther());
            table.addCell(wh.getIdNumber());
        }

        document.add(table);
    }
}
