package com.anirudhm.dinetime.views;

import com.anirudhm.dinetime.models.Order;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class OrdersExcelView extends AbstractXlsxView {

    private final List<Order> orders;
    private final String restaurantName;

    public OrdersExcelView(List<Order> orders, String restaurantName) {
        this.orders = orders;
        this.restaurantName = restaurantName;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) {
        String filename = "orders-" + restaurantName.replaceAll("\\s+", "_") + "-" + LocalDate.now() + ".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename=" + filename);

        Sheet sheet = workbook.createSheet("Orders");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Order ID");
        headerRow.createCell(1).setCellValue("Customer");
        headerRow.createCell(2).setCellValue("Status");
        headerRow.createCell(3).setCellValue("Total Price");

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            Row row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(order.getOrderId());
            row.createCell(1).setCellValue(order.getUser().getFname() + " " + order.getUser().getLname());
            row.createCell(2).setCellValue(order.getStatus());
            row.createCell(3).setCellValue(order.getTotalPrice());
        }

        for (int columnIndex = 0; columnIndex < 4; columnIndex++) {
            sheet.autoSizeColumn(columnIndex);
        }
    }
}
