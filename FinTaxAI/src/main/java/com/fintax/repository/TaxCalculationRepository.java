package com.fintax.repository;

import com.fintax.model.TaxCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaxCalculationRepository extends JpaRepository<TaxCalculation, Long> {
    List<TaxCalculation> findByTaxTypeOrderByCalculatedAtDesc(String taxType);
    List<TaxCalculation> findTop10ByOrderByCalculatedAtDesc();
}
