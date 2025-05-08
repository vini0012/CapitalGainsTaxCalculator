package service.strategy;

import model.Operation;
import model.TaxResult;
import service.TaxCalculationContext;

public sealed interface TaxCalculationStrategy permits BuyStrategy, SellStrategy {
    TaxResult calculate(Operation operation, TaxCalculationContext context);
}
