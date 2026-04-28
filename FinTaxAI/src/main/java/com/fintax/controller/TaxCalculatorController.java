package com.fintax.controller;

import com.fintax.model.TaxCalculation;
import com.fintax.service.TaxCalculationService;
import com.fintax.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tax")
public class TaxCalculatorController {
    
    @Autowired
    private TaxCalculationService taxCalculationService;
    
    @Autowired
    private PdfService pdfService;
    
    @PostMapping("/income")
    public String calculateIncomeTax(
            @RequestParam double income,
            @RequestParam(defaultValue = "0") double deductions,
            @RequestParam(required = false) String userName,
            Model model) {
        
        double taxAmount = taxCalculationService.calculateIncomeTax(income, deductions);
        double taxableIncome = income - deductions;
        double effectiveRate = taxableIncome > 0 ? (taxAmount / taxableIncome) * 100 : 0;
        
        TaxCalculation calculation = new TaxCalculation();
        calculation.setTaxType("Income Tax");
        calculation.setIncome(income);
        calculation.setDeductions(deductions);
        calculation.setTaxAmount(taxAmount);
        calculation.setEffectiveRate(effectiveRate);
        calculation.setUserName(userName);
        
        calculation = taxCalculationService.saveTaxCalculation(calculation);
        
        model.addAttribute("calculation", calculation);
        model.addAttribute("taxableIncome", taxableIncome);
        return "result";
    }
    
    @PostMapping("/corporate")
    public String calculateCorporateTax(
            @RequestParam double profit,
            @RequestParam(required = false) String userName,
            Model model) {
        
        double taxAmount = taxCalculationService.calculateCorporateTax(profit);
        double effectiveRate = 25.0;
        
        TaxCalculation calculation = new TaxCalculation();
        calculation.setTaxType("Corporate Tax");
        calculation.setProfit(profit);
        calculation.setTaxAmount(taxAmount);
        calculation.setEffectiveRate(effectiveRate);
        calculation.setUserName(userName);
        
        calculation = taxCalculationService.saveTaxCalculation(calculation);
        
        model.addAttribute("calculation", calculation);
        return "result";
    }
    
    @PostMapping("/sales")
    public String calculateSalesTax(
            @RequestParam double salesAmount,
            @RequestParam double gstRate,
            @RequestParam(required = false) String userName,
            Model model) {
        
        double taxAmount = taxCalculationService.calculateSalesTax(salesAmount, gstRate);
        
        TaxCalculation calculation = new TaxCalculation();
        calculation.setTaxType("Sales/GST Tax");
        calculation.setSalesAmount(salesAmount);
        calculation.setTaxAmount(taxAmount);
        calculation.setEffectiveRate(gstRate);
        calculation.setUserName(userName);
        
        calculation = taxCalculationService.saveTaxCalculation(calculation);
        
        model.addAttribute("calculation", calculation);
        return "result";
    }
    
    @PostMapping("/property")
    public String calculatePropertyTax(
            @RequestParam double propertyValue,
            @RequestParam double propertyArea,
            @RequestParam(required = false) String userName,
            Model model) {
        
        double taxAmount = taxCalculationService.calculatePropertyTax(propertyValue, propertyArea);
        double effectiveRate = propertyValue > 0 ? (taxAmount / propertyValue) * 100 : 0;
        
        TaxCalculation calculation = new TaxCalculation();
        calculation.setTaxType("Property Tax");
        calculation.setPropertyValue(propertyValue);
        calculation.setPropertyArea(propertyArea);
        calculation.setTaxAmount(taxAmount);
        calculation.setEffectiveRate(effectiveRate);
        calculation.setUserName(userName);
        
        calculation = taxCalculationService.saveTaxCalculation(calculation);
        
        model.addAttribute("calculation", calculation);
        return "result";
    }
    
    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
        TaxCalculation calculation = taxCalculationService.getAllCalculations().stream()
            .filter(c -> c.getId().equals(id))
            .findFirst()
            .orElse(null);
        
        if (calculation == null) {
            return ResponseEntity.notFound().build();
        }
        
        byte[] pdfBytes = pdfService.generateTaxReport(calculation);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "tax-report-" + id + ".pdf");
        
        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}
