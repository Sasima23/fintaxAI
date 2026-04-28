package com.fintax.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "tax_calculations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaxCalculation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String taxType;
    
    private Double income;
    private Double deductions;
    private Double profit;
    private Double salesAmount;
    private Double propertyValue;
    private Double propertyArea;
    
    @Column(nullable = false)
    private Double taxAmount;
    
    @Column(nullable = false)
    private Double effectiveRate;
    
    private String userName;
    
    @Column(nullable = false)
    private LocalDateTime calculatedAt;
    
    @PrePersist
    protected void onCreate() {
        calculatedAt = LocalDateTime.now();
    }
}
