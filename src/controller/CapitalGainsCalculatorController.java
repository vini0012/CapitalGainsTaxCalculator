package controller;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.Operation;
import model.TaxResult;
import service.CapitalGainsCalculatorService;

public class CapitalGainsCalculatorController {

    private final CapitalGainsCalculatorService calculatorService;
    private final ObjectMapper mapper;

    public CapitalGainsCalculatorController() {
        this.calculatorService = new CapitalGainsCalculatorService();
        this.mapper = new ObjectMapper();
    }

    public List<TaxResult> processOperations(String jsonOperations) throws Exception {
        List<Operation> operations = mapper.readValue(jsonOperations, new TypeReference<>() {});
        return calculatorService.calculateTaxes(operations);
    }

    public String convertResultsToJson(List<TaxResult> results) throws Exception {
        return mapper.writeValueAsString(results);
    }
}
