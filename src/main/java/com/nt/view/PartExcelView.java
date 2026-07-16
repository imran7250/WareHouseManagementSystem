package com.nt.view;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.nt.model.Part;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PartExcelView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(
            Map<String, Object> model,
            Workbook workbook,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition",
                "attachment; filename=Parts.xlsx");

        Sheet sheet = workbook.createSheet("PART DETAILS");

        // Header Row
        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("PART CODE");
        header.createCell(2).setCellValue("LENGTH");
        header.createCell(3).setCellValue("WIDTH");
        header.createCell(4).setCellValue("HEIGHT");
        header.createCell(5).setCellValue("COST");
        header.createCell(6).setCellValue("CURRENCY");
        header.createCell(7).setCellValue("UOM");
        header.createCell(8).setCellValue("SALE ORDER");

        @SuppressWarnings("unchecked")
        List<Part> list = (List<Part>) model.get("list");

        int rowNum = 1;

        for (Part part : list) {

            Row row = sheet.createRow(rowNum++);

            // ID
            row.createCell(0).setCellValue(
                    part.getId() != null ? part.getId() : 0);

            // Part Code
            row.createCell(1).setCellValue(
                    part.getPartCode() != null ? part.getPartCode() : "");

            // Length
            if (part.getPartLen() != null)
                row.createCell(2).setCellValue(part.getPartLen());
            else
                row.createCell(2).setCellValue("");

            // Width
            if (part.getPartWid() != null)
                row.createCell(3).setCellValue(part.getPartWid());
            else
                row.createCell(3).setCellValue("");

            // Height
            if (part.getPartHght() != null)
                row.createCell(4).setCellValue(part.getPartHght());
            else
                row.createCell(4).setCellValue("");

            // Cost
            if (part.getPartCost() != null)
                row.createCell(5).setCellValue(part.getPartCost());
            else
                row.createCell(5).setCellValue("");

            // Currency
            row.createCell(6).setCellValue(
                    part.getPartCurrency() != null ? part.getPartCurrency() : "");

            // UOM
            row.createCell(7).setCellValue(
                    part.getUom() != null
                            ? part.getUom().getUomModel()
                            : "");

            // Sale Order
            row.createCell(8).setCellValue(
                    part.getOmSale() != null
                            ? part.getOmSale().getOrderCode()
                            : "");
        }

        // Auto Size Columns
        for (int i = 0; i < 9; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}

//package com.nt.view;
//
//import java.util.List;
//import java.util.Map;
//
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.springframework.web.servlet.view.document.AbstractXlsxView;
//
//import com.nt.model.Part;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//public class PartExcelView extends AbstractXlsxView {
//
//    @Override
//    protected void buildExcelDocument(
//            Map<String, Object> model,
//            Workbook workbook,
//            HttpServletRequest request,
//            HttpServletResponse response) throws Exception {
//
//        response.setHeader("Content-Disposition",
//                "attachment; filename=Parts.xlsx");
//
//        Sheet sheet = workbook.createSheet("PART DETAILS");   
//
//        Row header = sheet.createRow(0);
//
//        header.createCell(0).setCellValue("ID");
//        header.createCell(1).setCellValue("PART CODE");
//        header.createCell(2).setCellValue("LENGTH");
//        header.createCell(3).setCellValue("WIDTH");
//        header.createCell(4).setCellValue("HEIGHT");
//        header.createCell(5).setCellValue("COST");
//        header.createCell(6).setCellValue("CURRENCY");
//        header.createCell(7).setCellValue("UOM");
//        header.createCell(8).setCellValue("SALE ORDER");
//
//        @SuppressWarnings("unchecked")
//        List<Part> list = (List<Part>) model.get("list");
//
//        int rowNum = 1;
//
//        for (Part part : list) {
//
//            Row row = sheet.createRow(rowNum++);
//
//            row.createCell(0).setCellValue(part.getId());
//            row.createCell(1).setCellValue(part.getPartCode());
//            row.createCell(2).setCellValue(part.getPartLen());
//            row.createCell(3).setCellValue(part.getPartWid());
//            row.createCell(4).setCellValue(part.getPartHght());
//            row.createCell(5).setCellValue(part.getPartCost());
//            row.createCell(6).setCellValue(part.getPartCurrency());
//
//            row.createCell(7).setCellValue(
//                    part.getUom() != null ? part.getUom().getUomModel() : "");
//
//            row.createCell(8).setCellValue(
//                    part.getOmSale() != null ? part.getOmSale().getOrderCode() : "");
//        }
//
//        for (int i = 0; i < 9; i++) {
//            sheet.autoSizeColumn(i);
//        }
//    }
//}
