package com.fintax.service;

import com.fintax.model.TaxCalculation;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {
    
    public byte[] generateTaxReport(TaxCalculation calculation) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        try {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);
            
            document.add(new Paragraph("FinTax AI - Tax Calculation Report")
                .setFontSize(20)
                .setBold()
                .setTextAlignment(TextAlignment.CENTER));
            
            document.add(new Paragraph("\n"));
            
            document.add(new Paragraph("Tax Type: " + calculation.getTaxType())
                .setFontSize(14));
            
            document.add(new Paragraph("Calculated On: " + 
                calculation.getCalculatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                .setFontSize(12));
            
            if (calculation.getUserName() != null) {
                document.add(new Paragraph("Name: " + calculation.getUserName())
                    .setFontSize(12));
            }
            
            document.add(new Paragraph("\n"));
            
            Table table = new Table(2);
            table.setWidth(400);
            
            switch (calculation.getTaxType()) {
                case "Income Tax":
                    table.addCell("Gross Income");
                    table.addCell(String.format("₹%.2f", calculation.getIncome()));
                    table.addCell("Deductions");
                    table.addCell(String.format("₹%.2f", calculation.getDeductions()));
                    table.addCell("Taxable Income");
                    table.addCell(String.format("₹%.2f", calculation.getIncome() - calculation.getDeductions()));
                    break;
                case "Corporate Tax":
                    table.addCell("Company Profit");
                    table.addCell(String.format("₹%.2f", calculation.getProfit()));
                    break;
                case "Sales/GST Tax":
                    table.addCell("Sales Amount");
                    table.addCell(String.format("₹%.2f", calculation.getSalesAmount()));
                    break;
                case "Property Tax":
                    table.addCell("Property Value");
                    table.addCell(String.format("₹%.2f", calculation.getPropertyValue()));
                    table.addCell("Property Area (sq ft)");
                    table.addCell(String.format("%.2f", calculation.getPropertyArea()));
                    break;
            }
            
            table.addCell("Tax Amount");
            table.addCell(String.format("₹%.2f", calculation.getTaxAmount()));
            table.addCell("Effective Rate");
            table.addCell(String.format("%.2f%%", calculation.getEffectiveRate()));
            
            document.add(table);
            
            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("This is a computer-generated report from FinTax AI")
                .setFontSize(10)
                .setTextAlignment(TextAlignment.CENTER));
            
            document.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return baos.toByteArray();
    }
}
