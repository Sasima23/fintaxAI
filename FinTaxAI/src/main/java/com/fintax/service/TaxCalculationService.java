package com.fintax.service;

import com.fintax.model.TaxCalculation;
import com.fintax.repository.TaxCalculationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TaxCalculationService {
    
    @Autowired
    private TaxCalculationRepository repository;
    
    public double calculateIncomeTax(double income, double deductions) {
        double taxableIncome = income - deductions;
        if (taxableIncome <= 0) return 0;
        
        double baseTax = 0;
        
        if (taxableIncome <= 300000) {
            baseTax = 0;
        } else if (taxableIncome <= 600000) {
            baseTax = (taxableIncome - 300000) * 0.05;
        } else if (taxableIncome <= 900000) {
            baseTax = (300000 * 0.05) + (taxableIncome - 600000) * 0.10;
        } else if (taxableIncome <= 1200000) {
            baseTax = (300000 * 0.05) + (300000 * 0.10) + (taxableIncome - 900000) * 0.15;
        } else if (taxableIncome <= 1500000) {
            baseTax = (300000 * 0.05) + (300000 * 0.10) + (300000 * 0.15) + (taxableIncome - 1200000) * 0.20;
        } else {
            baseTax = (300000 * 0.05) + (300000 * 0.10) + (300000 * 0.15) + (300000 * 0.20) + (taxableIncome - 1500000) * 0.30;
        }
        
        if (taxableIncome <= 700000) {
            double rebate = Math.min(25000, baseTax);
            baseTax = Math.max(0, baseTax - rebate);
        } else {
            double rebate = 25000;
            double taxAfterRebate = baseTax - rebate;
            double incomeExcess = taxableIncome - 700000;
            baseTax = Math.min(taxAfterRebate, incomeExcess);
        }
        
        double finalTax = baseTax * 1.04;
        
        return finalTax;
    }
    
    public double calculateCorporateTax(double profit) {
        if (profit <= 0) return 0;
        return profit * 0.25;
    }
    
    public double calculateSalesTax(double salesAmount, double gstRate) {
        if (salesAmount <= 0) return 0;
        return salesAmount * (gstRate / 100);
    }
    
    public double calculatePropertyTax(double propertyValue, double propertyArea) {
        if (propertyValue <= 0 || propertyArea <= 0) return 0;
        double baseRate = 0.01;
        return propertyValue * baseRate + (propertyArea * 50);
    }
    
    public TaxCalculation saveTaxCalculation(TaxCalculation calculation) {
        return repository.save(calculation);
    }
    
    public List<TaxCalculation> getAllCalculations() {
        return repository.findTop10ByOrderByCalculatedAtDesc();
    }
    
    public List<TaxCalculation> getCalculationsByType(String taxType) {
        return repository.findByTaxTypeOrderByCalculatedAtDesc(taxType);
    }
}
