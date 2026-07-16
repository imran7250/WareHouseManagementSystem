package com.nt.view;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.nt.model.OrderMethod;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class OrderMethodExcelView extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(
            Map<String, Object> model,
            XSSFWorkbook workbook,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        response.addHeader("Content-Disposition",
                "attachment;filename=OrderMethods.xlsx");

        Sheet sheet = workbook.createSheet("ORDER METHODS");

        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("MODE");
        header.createCell(2).setCellValue("CODE");
        header.createCell(3).setCellValue("TYPE");
        header.createCell(4).setCellValue("ACCEPT");
        header.createCell(5).setCellValue("DESCRIPTION");

        @SuppressWarnings("unchecked")
        List<OrderMethod> list =
                (List<OrderMethod>) model.get("list");

        int rowNum = 1;

        for (OrderMethod om : list) {

            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(om.getId());
            row.createCell(1).setCellValue(om.getOrderMode());
            row.createCell(2).setCellValue(om.getOrderCode());
            row.createCell(3).setCellValue(om.getOrderType());

            row.createCell(4).setCellValue(
                    String.join(",", om.getOrderAcpt()));

            row.createCell(5).setCellValue(om.getDescription());
        }
    }
}
