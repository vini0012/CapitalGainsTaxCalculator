package service.strategy;

import static utils.BigDecimalUtils.bigDecimalZero;
import static utils.BigDecimalUtils.roundToTwoDecimalPlaces;

import java.math.BigDecimal;
import java.util.List;

import model.Operation;
import model.TaxResult;
import service.TaxCalculationContext;

public final class SellStrategy implements TaxCalculationStrategy {

    private static final BigDecimal TAX_RATE = new BigDecimal("0.20");
    private static final BigDecimal NO_TAX = bigDecimalZero();

    private final List<OperationHandlerFactory.SellOperationHandler> handlers;

    public SellStrategy() {
        this.handlers = OperationHandlerFactory.createHandlers();
    }

    @Override
    public TaxResult calculate(Operation operation, TaxCalculationContext context) {
        return handlers.stream()
                .filter(handler -> handler.isApplicable(operation, context))
                .findFirst()
                .map(handler -> handler.handle(operation, context))
                .orElseGet(() -> calculateTaxResult(operation, context));
    }

    private TaxResult calculateTaxResult(Operation operation, TaxCalculationContext context) {
        BigDecimal taxableProfit = adjustProfitForAccumulatedLoss(operation, context);
        BigDecimal tax = calculateTax(taxableProfit);
        context.updateQuantityAfterSell(operation.quantity());
        return new TaxResult(tax);
    }

    private BigDecimal adjustProfitForAccumulatedLoss(Operation operation, TaxCalculationContext context) {
        BigDecimal grossProfit = operation.calculateGrossProfit(context.getWeightedAverage());
        return context.applyAccumulatedLoss(grossProfit);
    }

    private BigDecimal calculateTax(BigDecimal taxableProfit) {
        return taxableProfit.compareTo(BigDecimal.ZERO) > 0
                ? roundToTwoDecimalPlaces(taxableProfit.multiply(TAX_RATE))
                : NO_TAX;
    }
}