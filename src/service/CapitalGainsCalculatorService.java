package service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import enums.OperationType;
import model.Operation;
import model.TaxResult;
import service.strategy.BuyStrategy;
import service.strategy.SellStrategy;
import service.strategy.TaxCalculationStrategy;

public class CapitalGainsCalculatorService {

    private final Map<OperationType, TaxCalculationStrategy> strategies;

    public CapitalGainsCalculatorService() {
        strategies = Map.of(
                OperationType.BUY, new BuyStrategy(),
                OperationType.SELL, new SellStrategy()
        );
    }

    public List<TaxResult> calculateTaxes(List<Operation> operations) {
        TaxCalculationContext context = new TaxCalculationContext();

        return operations.stream()
                .map(operation -> strategies.get(operation.operationType()).calculate(operation, context))
                .collect(Collectors.toList());
    }
}