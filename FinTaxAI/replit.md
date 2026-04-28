# FinTax AI - Smart Multi-Tax Calculator with Chatbot

## Overview
FinTax AI is a comprehensive tax management and calculation web application built with Java Spring Boot and Thymeleaf. The application helps users calculate different types of taxes (Income Tax, Corporate Tax, Sales/GST Tax, and Property Tax) and provides tax guidance through an AI-powered chatbot.

## Project Status
- **Created**: November 11, 2025
- **Status**: Active Development
- **Tech Stack**: Java Spring Boot, Thymeleaf, PostgreSQL, Bootstrap, Chart.js

## Features
1. **Income Tax Calculator** - Slab-based calculation with deduction support
2. **Corporate Tax Calculator** - 25% tax on company profits
3. **Sales/GST Tax Calculator** - Customizable GST rates (5%, 12%, 18%, 28%)
4. **Property Tax Calculator** - Based on property value and area
5. **AI Chatbot** - OpenAI-powered tax assistant for queries
6. **Chart Visualizations** - Tax breakdown charts using Chart.js
7. **PDF Export** - Generate PDF reports using iTextPDF
8. **Calculation History** - Track all tax calculations

## Project Structure
```
src/
├── main/
│   ├── java/com/fintax/
│   │   ├── FinTaxApplication.java (Main application)
│   │   ├── model/
│   │   │   └── TaxCalculation.java (Entity model)
│   │   ├── repository/
│   │   │   └── TaxCalculationRepository.java (Data access)
│   │   ├── service/
│   │   │   ├── TaxCalculationService.java (Business logic)
│   │   │   ├── ChatbotService.java (OpenAI integration)
│   │   │   └── PdfService.java (PDF generation)
│   │   └── controller/
│   │       ├── HomeController.java (Page routing)
│   │       ├── TaxCalculatorController.java (Tax calculations)
│   │       └── ChatbotController.java (Chat API)
│   └── resources/
│       ├── application.properties (Configuration)
│       ├── templates/ (Thymeleaf HTML templates)
│       └── static/ (CSS, JavaScript)
```

## Tax Calculation Logic

### Income Tax (Indian Slabs - New Regime 2023+)
- Up to ₹3 lakh: 0%
- ₹3 - ₹6 lakh: 5%
- ₹6 - ₹9 lakh: 10%
- ₹9 - ₹12 lakh: 15%
- ₹12 - ₹15 lakh: 20%
- Above ₹15 lakh: 30%
- Plus 4% Health & Education Cess on tax amount
- Section 87A Rebate: Up to ₹25,000 for taxable income ≤ ₹7 lakh
- Marginal Relief: For incomes above ₹7 lakh, tax cannot exceed income excess over ₹7 lakh

### Corporate Tax
- Flat 25% on company profit

### Sales/GST Tax
- Configurable rates: 5%, 12%, 18%, 28%

### Property Tax
- 1% of property value + ₹50 per sq ft

## Environment Variables
- `DATABASE_URL` - PostgreSQL connection URL (auto-configured)
- `OPENAI_API_KEY` - OpenAI API key for chatbot (required for AI features)

## Running the Application
The application runs on port 5000 and is accessible via the webview.

## Dependencies
- Spring Boot 3.1.5
- Spring Data JPA
- PostgreSQL Driver
- Thymeleaf
- iText7 (PDF generation)
- Lombok
- Bootstrap 5
- Chart.js

## Recent Changes
- November 11, 2025: Initial project creation with all core features
- November 11, 2025: Updated income tax calculation to use 2023+ new regime slabs with 4% cess, Section 87A rebate, and marginal relief
- November 11, 2025: Verified all tax calculations are correct and application is running successfully on port 5000

## Notes for Future Development
- Consider adding unit tests for tax calculation scenarios (₹6.5L, ₹7L, ₹7.1L, ₹8L)
- Document marginal relief logic in code comments for maintainers
- Optional: Add Spring Security for user authentication (currently optional feature)
- To use the AI chatbot feature, set the OPENAI_API_KEY environment variable
