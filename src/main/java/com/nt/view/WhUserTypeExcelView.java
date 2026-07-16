package com.nt.view;

import java.util.List;
import java.util.Map;   

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.nt.model.WhUserType;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WhUserTypeExcelView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(
            Map<String, Object> model,
            Workbook workbook,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        response.setHeader(
                "Content-Disposition",
                "attachment; filename=WhUserType.xlsx");

        Sheet sheet = workbook.createSheet("WH USER TYPES");

        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("USER TYPE");
        header.createCell(2).setCellValue("USER CODE");
        header.createCell(3).setCellValue("USER FOR");
        header.createCell(4).setCellValue("EMAIL");
        header.createCell(5).setCellValue("CONTACT");
        header.createCell(6).setCellValue("ID TYPE");
        header.createCell(7).setCellValue("IF OTHER");
        header.createCell(8).setCellValue("ID NUMBER");

        @SuppressWarnings("unchecked")
        List<WhUserType> list =
                (List<WhUserType>) model.get("list");

        int rowNum = 1;

        for (WhUserType wh : list) {

            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(
                    wh.getId() != null ? wh.getId() : 0);

            row.createCell(1).setCellValue(
                    wh.getUserType() != null ? wh.getUserType() : "");

            row.createCell(2).setCellValue(
                    wh.getUserCode() != null ? wh.getUserCode() : "");

            row.createCell(3).setCellValue(
                    wh.getUserFor() != null ? wh.getUserFor() : "");

            row.createCell(4).setCellValue(
                    wh.getUserEmail() != null ? wh.getUserEmail() : "");

            row.createCell(5).setCellValue(
                    wh.getUserContact() != null ? wh.getUserContact() : "");

            row.createCell(6).setCellValue(
                    wh.getUserIdType() != null ? wh.getUserIdType() : "");

            row.createCell(7).setCellValue(
                    wh.getIfOther() != null ? wh.getIfOther() : "");

            row.createCell(8).setCellValue(
                    wh.getIdNumber() != null ? wh.getIdNumber() : "");
        }

        for (int i = 0; i < 9; i++) {
            sheet.autoSizeColumn(i);
        }
    }
}
